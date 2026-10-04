package src.com.kodewala.ecommerce.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.ProductNotFoundException;
import src.com.kodewala.ecommerce.model.Product;
import src.com.kodewala.ecommerce.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
// add products by admin and some products are hardcoded .
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }
//used  admin see and customer view 
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
// used in addcart, cancelOrder, place order, increase the quantity, update the price, update the quantity
    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " does not exist."));
    }
 // customer in serarch oprt on main
    public List<Product> searchByName(String name) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
 // customer in serarch oprt on main
    public List<Product> searchByCategory(String category) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
// customer in serarch oprt on main
    public List<Product> searchByBrand(String brand) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }
// customer in serarch oprt on main
    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }
// admin update price
    public void updateProductPrice(int productId, double newPrice) {
        Product product = getProductById(productId);
        product.setPrice(newPrice);
    }
//admin  customer update the quantity of product , cancel order ,place order
    public void updateProductQuantity(int productId, int newQuantity) {
        Product product = getProductById(productId);
        product.setQuantity(newQuantity);
    }
// admin delete product
    public void deleteProduct(int productId) {
        boolean removed = productRepository.deleteProduct(productId);
        if (!removed) {
            throw new ProductNotFoundException("Product with ID " + productId + " does not exist.");
        }
    }
}