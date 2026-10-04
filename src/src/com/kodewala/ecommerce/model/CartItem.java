package src.com.kodewala.ecommerce.model;

public class CartItem {
	private int productId;
	private String productName;
	private double price;
	private int quantity;
	private double totalPrice;

	public CartItem() {
	}

	public CartItem(int productId, String productName, double price, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.totalPrice = price * quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
		this.totalPrice = this.price * this.quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.totalPrice = this.price * this.quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	@Override
	public String toString() {
		return "CartItem [productId=" + productId + ", productName=" + productName + ", price=" + price + ", quantity="
				+ quantity + ", totalPrice=" + totalPrice + "]";
	}

}