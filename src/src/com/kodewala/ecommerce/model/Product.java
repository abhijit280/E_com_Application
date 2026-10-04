package src.com.kodewala.ecommerce.model;

import java.util.Objects;

public class Product {
	private int productId;
	private String name;
	private String category;
	private String brand;
	private double price;
	private int quantity;

	public Product() {
	}

	public Product(int productId, String name, String category, String brand, double price, int quantity) {
		this.productId = productId;
		this.name = name;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (!(o instanceof Product))
//			return false;
//		Product product = (Product) o;
//		return productId == product.productId;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(productId);
//	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + " | , name=" + name + " | , category=" + category + " | , brand=" + brand
				+ " | , price=" + price + " | , quantity=" + quantity + "]";
	}

}