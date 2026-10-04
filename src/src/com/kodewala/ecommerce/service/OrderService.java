package src.com.kodewala.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import Exceptions.InsufficientStockException;
import Exceptions.InvalidOrderException;
import src.com.kodewala.ecommerce.model.CartItem;
import src.com.kodewala.ecommerce.model.Order;
import src.com.kodewala.ecommerce.model.OrderStatus;
import src.com.kodewala.ecommerce.model.Product;
import src.com.kodewala.ecommerce.repository.OrderRepository;

public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final AtomicInteger orderIdGenerator = new AtomicInteger(1000);

    public OrderService(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }
    public Order placeOrder(int customerId) {
        List<CartItem> cart = cartService.getCart(customerId);
        if (cart.isEmpty()) {
            throw new InvalidOrderException("Cannot place order. Shopping cart is empty.");
        }
        // Validate inventory for each item
        for (CartItem item : cart) {
            Product product = productService.getProductById(item.getProductId());
            if (product.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName() 
                        + ". Available: " + product.getQuantity() + ", Requested: " + item.getQuantity());
            }
        }
        // Deduct inventory
        for (CartItem item : cart) {
            Product product = productService.getProductById(item.getProductId());
            productService.updateProductQuantity(product.getProductId(), product.getQuantity() - item.getQuantity());
        }
        double totalAmount = cartService.calculateCartTotal(customerId);
        int generatedOrderId = orderIdGenerator.incrementAndGet();
        // Create snapshot copy of items for the order
        List<CartItem> orderItems = new ArrayList<>(cart);
		Order newOrder = new Order(generatedOrderId, customerId, orderItems, totalAmount, OrderStatus.PLACED,LocalDateTime.now());
        orderRepository.addOrder(newOrder);
        // Clear cart after successful order
        cartService.clearCart(customerId);
        return newOrder;
    }
    public Order getOrderDetails(int orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new InvalidOrderException("Order with ID " + orderId + " does not exist."));
    }
//see the order
    public List<Order> getCustomerOrders(int customerId) {
        return orderRepository.getAllOrders().stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }
// cancel order
    public void cancelOrder(int orderId, int customerId) {
        Order order = getOrderDetails(orderId);
        if (order.getCustomerId() != customerId) {
            throw new InvalidOrderException("You are not authorized to cancel this order.");
        }
        if (order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderException("Order cannot be cancelled in status: " + order.getOrderStatus());
        }
        // Restore inventory
        for (CartItem item : order.getItems()) {
            Product product = productService.getProductById(item.getProductId());
            productService.updateProductQuantity(product.getProductId(), product.getQuantity() + item.getQuantity());
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
    }
}