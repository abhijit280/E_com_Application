package src.com.kodewala.ecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.com.kodewala.ecommerce.model.Product;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> findById(int productId) {
        return products.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst();
    }

    public boolean deleteProduct(int productId) {
        return products.removeIf(p -> p.getProductId() == productId);
    }
}