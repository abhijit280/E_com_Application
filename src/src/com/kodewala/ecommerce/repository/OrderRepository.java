package src.com.kodewala.ecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.com.kodewala.ecommerce.model.Order;

public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Optional<Order> findByOrderId(int orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst();
    }
}