package src.com.kodewala.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Exceptions.InsufficientStockException;
import Exceptions.InvalidQuantityException;
import src.com.kodewala.ecommerce.model.CartItem;
import src.com.kodewala.ecommerce.model.Product;

public class CartService {
    // Map of CustomerID -> List of CartItems
    private final Map<Integer, List<CartItem>> cartMap = new HashMap<>();
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }
//get the cart on specific customer
    public List<CartItem> getCart(int customerId) {
        return cartMap.computeIfAbsent(customerId, k -> new ArrayList<>());
    }
// add products in cart
    public void addToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero.");
        }

        Product product = productService.getProductById(productId);
        List<CartItem> cart = getCart(customerId);

        Optional<CartItem> existingItemOpt = cart.stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst();

        int currentCartQty = existingItemOpt.map(CartItem::getQuantity).orElse(0);
        if (currentCartQty + quantity > product.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock. Available: " + product.getQuantity());
        }

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.add(new CartItem(product.getProductId(), product.getName(), product.getPrice(), quantity));
        }
    }
// remove the item inside the cart
    public void removeFromCart(int customerId, int productId) {
        List<CartItem> cart = getCart(customerId);
        cart.removeIf(item -> item.getProductId() == productId);
    }

    public void increaseQuantity(int customerId, int productId, int addQty) {
        if (addQty <= 0) {
            throw new InvalidQuantityException("Increase value must be greater than zero.");
        }
        Product product = productService.getProductById(productId);
        List<CartItem> cart = getCart(customerId);

        CartItem item = cart.stream()
                .filter(i -> i.getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new InvalidQuantityException("Product not found in cart."));

        if (item.getQuantity() + addQty > product.getQuantity()) {
            throw new InsufficientStockException("Cannot increase quantity. Max available: " + product.getQuantity());
        }
        item.setQuantity(item.getQuantity() + addQty);
    }
// decrease the quantity from cart
    public void decreaseQuantity(int customerId, int productId, int subQty) {
        if (subQty <= 0) {
            throw new InvalidQuantityException("Decrease value must be greater than zero.");
        }
        List<CartItem> cart = getCart(customerId);
        CartItem item = cart.stream()
                .filter(i -> i.getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new InvalidQuantityException("Product not found in cart."));

        int remaining = item.getQuantity() - subQty;
        if (remaining <= 0) {
            cart.remove(item);
        } else {
            item.setQuantity(remaining);
        }
    }
// Total price of cart item
    public double calculateCartTotal(int customerId) {
        List<CartItem> cart = getCart(customerId);
        return cart.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
//  delete everything from cart
    public void clearCart(int customerId) {
        List<CartItem> cart = cartMap.get(customerId);
        if (cart != null) {
            cart.clear();
        }
    }
 // move all items from one cart to another (guest cart -> customer cart)
    public void moveCart(int fromId, int toId) {
        List<CartItem> fromCart = getCart(fromId);
        for (CartItem item : new ArrayList<>(fromCart)) {
            addToCart(toId, item.getProductId(), item.getQuantity());   // merges quantity and checks stock
            removeFromCart(fromId, item.getProductId());
        }
    }
}