package src.com.kodewala.ecommerce.main;

import java.util.List;
import java.util.Scanner;

import src.com.kodewala.ecommerce.model.CartItem;
import src.com.kodewala.ecommerce.model.Customer;
import src.com.kodewala.ecommerce.model.Order;
import src.com.kodewala.ecommerce.model.Product;
import src.com.kodewala.ecommerce.repository.CustomerRepository;
import src.com.kodewala.ecommerce.repository.OrderRepository;
import src.com.kodewala.ecommerce.repository.ProductRepository;
import src.com.kodewala.ecommerce.service.CartService;
import src.com.kodewala.ecommerce.service.CustomerService;
import src.com.kodewala.ecommerce.service.OrderService;
import src.com.kodewala.ecommerce.service.ProductService;

public class EcommerceApplication {
	private static final Scanner scanner = new Scanner(System.in);
	private static ProductService productService;
	private static CustomerService customerService;
	private static CartService cartService;
	private static OrderService orderService;
	private static final int GUEST_ID = -1;
	public static void main(String[] args) {
		repositoryAndServiceObject();
		hardCodedData();

		boolean running = true;
		while (running) {
			System.out.println("====== E-COMMERCE MANAGEMENT SYSTEM ======  ");
			System.out.println("1. Admin");
			System.out.println("2. Customer");
			System.out.println("3. Guest User");
			System.out.println("4. Exit");
			System.out.print("Enter choice: ");

			int choice = readIntInput();
			switch (choice) {
			case 1:
				adminMenu();
				break;
			case 2:
				handleCustomerEntryMenu();
				break;
			case 3:
				System.out.println("=========== Guest ===========");
				guestUser();
				break;
			case 4:
				System.out.println("Thank you for using the application. Goodbye!");
				running = false;
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static void repositoryAndServiceObject() {
		ProductRepository productRepo = new ProductRepository();
		CustomerRepository customerRepo = new CustomerRepository();
		OrderRepository orderRepo = new OrderRepository();

		productService = new ProductService(productRepo);
		customerService = new CustomerService(customerRepo);
		cartService = new CartService(productService);
		orderService = new OrderService(orderRepo, cartService, productService);
	}

	private static void hardCodedData() {
		productService.addProduct(new Product(101, "iPhone 16", "Electronics", "Apple", 79999.0, 10));
		productService.addProduct(new Product(102, "Galaxy S24", "Electronics", "Samsung", 74999.0, 15));
		productService.addProduct(new Product(103, "Running Shoes", "Footwear", "Nike", 4999.0, 20));
		productService.addProduct(new Product(104, "Mechanical Keyboard", "Accessories", "Keychron", 6999.0, 8));
		productService.addProduct(new Product(105, "MacBook Air M3", "Electronics", "Apple", 114999.0, 7));
		productService.addProduct(new Product(106, "Sony WH-1000XM5", "Audio", "Sony", 29999.0, 12));
		productService.addProduct(new Product(107, "Dell Inspiron 15", "Laptops", "Dell", 65999.0, 9));
		productService.addProduct(new Product(108, "Logitech MX Master 3S", "Accessories", "Logitech", 8999.0, 14));
		productService.addProduct(new Product(109, "AirPods Pro 2", "Audio", "Apple", 24999.0, 18));
		productService.addProduct(new Product(110, "Nike Air Max", "Footwear", "Nike", 8999.0, 16));
		productService.addProduct(new Product(111, "OnePlus 13", "Electronics", "OnePlus", 69999.0, 11));
		productService.addProduct(new Product(112, "Samsung Galaxy Tab S9", "Tablets", "Samsung", 67999.0, 6));
		productService.addProduct(new Product(113, "Adidas Ultraboost", "Footwear", "Adidas", 12999.0, 13));
		productService.addProduct(new Product(114, "JBL Flip 6", "Audio", "JBL", 9999.0, 20));
		productService.addProduct(new Product(115, "HP Pavilion 14", "Laptops", "HP", 61999.0, 8));
		productService.addProduct(new Product(116, "Canon EOS 1500D", "Cameras", "Canon", 42999.0, 5));
		productService.addProduct(new Product(117, "Mi Smart TV 55", "Television", "Xiaomi", 44999.0, 10));
		productService.addProduct(new Product(118, "Apple Watch Series 10", "Wearables", "Apple", 46999.0, 9));
		productService.addProduct(new Product(119, "Kindle Paperwhite", "Books", "Amazon", 14999.0, 15));
		productService.addProduct(new Product(120, "Backpack", "Accessories", "Wildcraft", 2999.0, 25));

		customerService.registerCustomer(
				new Customer(101, "Abhijit Pradhan", "Abhi@Pradhan.com", "7992798048", "Bhubaneswar,Odisha,752035"));
	}

	// Admin Menu >>>>>
	private static void adminMenu() {
		boolean back = false;
		while (!back) {
			System.out.println("\n--- Admin Menu ---");
			System.out.println("1. Add Product");
			System.out.println("2. View Products");
			System.out.println("3. Search Product");
			System.out.println("4. Update Product");
			System.out.println("5. Delete Product");
			System.out.println("6. View Customers");
			System.out.println("7. View All Orders");
			System.out.println("8. Exit");
			System.out.print("Enter choice: ");

			int choice = readIntInput();
			try {
				switch (choice) {
				case 1:
					System.out.print("Enter Product ID: ");
					int id = readIntInput();
					System.out.print("Enter Name: ");
					String name = scanner.nextLine();
					System.out.print("Enter Category: ");
					String cat = scanner.nextLine();
					System.out.print("Enter Brand: ");
					String brand = scanner.nextLine();
					System.out.print("Enter Price: ");
					double price = readDoubleInput();
					System.out.print("Enter Quantity: ");
					int qty = readIntInput();

					productService.addProduct(new Product(id, name, cat, brand, price, qty));
					System.out.println("Product added successfully.");
					break;
				case 2:
					productService.getAllProducts().forEach(System.out::println);
					break;
				case 3:
					handleSearchProduct();
					break;
				case 4:
					System.out.println("1. Update Price | 2. Update Quantity");
					int updateChoice = readIntInput();
					System.out.print("Enter Product ID: ");
					int pid = readIntInput();
					if (updateChoice == 1) {
						System.out.print("Enter New Price: ");
						double newPrice = readDoubleInput();
						productService.updateProductPrice(pid, newPrice);
						System.out.println("Price updated.");
					} else if (updateChoice == 2) {
						System.out.print("Enter New Quantity: ");
						int newQty = readIntInput();
						productService.updateProductQuantity(pid, newQty);
						System.out.println("Quantity updated.");
					}
					break;
				case 5:
					System.out.print("Enter Product ID to delete: ");
					int delId = readIntInput();
					productService.deleteProduct(delId);
					System.out.println("Product deleted successfully.");
					break;
				case 6:
					customerService.getAllCustomers().forEach(System.out::println);
					break;
				case 7:
					List<Order> orders = orderService.getAllOrders();
					if (orders.isEmpty()) {
						System.out.println("No orders found.");
					} else {
						orders.forEach(System.out::println);
					}
					break;
				case 8:
					back = true;
					break;
				default:
					System.out.println("Invalid selection.");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// guest portal
	private static void guestUser() {
	    boolean back = false;
	    while (!back) {
	        System.out.println("\n----- Guest Portal -----");
	        System.out.println("1. View Products");
	        System.out.println("2. Search Product");
	        System.out.println("3. Add Product to Cart");
	        System.out.println("4. View Cart");
	        System.out.println("5. Remove Product from Cart");
	        System.out.println("6. Place Order (Login required)");
	        System.out.println("7. Back");
	        System.out.print("Enter choice: ");

	        int choice = readIntInput();
	        try {
	            switch (choice) {
	            case 1:
	                productService.getAllProducts().forEach(System.out::println);
	                break;
	            case 2:
	                handleSearchProduct();
	                break;
	            case 3:
	                System.out.print("Enter Product ID: ");
	                int pid = readIntInput();
	                System.out.print("Enter Quantity: ");
	                int qty = readIntInput();
	                cartService.addToCart(GUEST_ID, pid, qty);
	                System.out.println("Added to cart.");
	                break;
	            case 4:
	                List<CartItem> cart = cartService.getCart(GUEST_ID);
	                if (cart.isEmpty()) {
	                    System.out.println("Cart is empty.");
	                } else {
	                    cart.forEach(System.out::println);
	                    System.out.println("Total Cart Value: " + cartService.calculateCartTotal(GUEST_ID));
	                }
	                break;
	            case 5:
	                System.out.print("Enter Product ID to remove: ");
	                int remPid = readIntInput();
	                cartService.removeFromCart(GUEST_ID, remPid);
	                System.out.println("Item removed from cart.");
	                break;
	            case 6:
	                if (guestPlaceOrder()) {
	                    back = true;          // order done, return to main menu
	                }
	                break;
	            case 7:
	                cartService.clearCart(GUEST_ID);   // guest cart is not kept
	                back = true;
	                break;
	            default:
	                System.out.println("Invalid selection.");
	            }
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	}
	// Customer Navigation >>>>>>>>>
	private static void handleCustomerEntryMenu() {
		boolean back = false;
		while (!back) {
			System.out.println("\n--- Customer Portal ---");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Back to Main Menu");
			System.out.print("Enter choice: ");

			int choice = readIntInput();
			switch (choice) {
			case 1:
				registerCustomerUI();
				break;
			case 2:
				System.out.print("Enter Customer ID to Login: ");
				int cid = readIntInput();
				try {
					Customer customer = customerService.getCustomerById(cid);
					System.out.println("Welcome back, " + customer.getName());
					handleCustomerOperations(customer);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
				break;
			case 3:
				back = true;
				break;
			default:
				System.out.println("Invalid selection.");
			}
		}
	}

	private static void registerCustomerUI() {
		System.out.print("Enter Customer ID: ");
		int id = readIntInput();
		System.out.print("Enter Name: ");
		String name = scanner.nextLine();
		System.out.print("Enter Email: ");
		String email = scanner.nextLine();
		System.out.print("Enter Mobile: ");
		String mobile = scanner.nextLine();
		System.out.print("Enter Address: ");
		String address = scanner.nextLine();
		customerService.registerCustomer(new Customer(id, name, email, mobile, address));
		System.out.println("Registration successful. Please login.");
	}
// customer menu
	private static void handleCustomerOperations(Customer customer) {
		boolean logout = false;
		while (!logout) {
			System.out.println("\n--- Customer Menu (" + customer.getName() + ") ---");
			System.out.println("1. View Products");
			System.out.println("2. Search Product");
			System.out.println("3. Add Product to Cart");
			System.out.println("4. View Cart");
			System.out.println("5. Remove Product from Cart");
			System.out.println("6. Place Order");
			System.out.println("7. View My Orders");
			System.out.println("8. Cancel Order");
			System.out.println("9. Update Address");
			System.out.println("10. Increase Cart Quantity");
			System.out.println("11. Decrease Cart Quantity");
			System.out.println("12. Logout");
			System.out.print("Enter choice: ");

			int choice = readIntInput();
			try {
				switch (choice) {
				case 1:
					productService.getAllProducts().forEach(System.out::println);
					break;
				case 2:
					handleSearchProduct();
					break;
				case 3:
					System.out.print("Enter Product ID: ");
					int pid = readIntInput();
					System.out.print("Enter Quantity: ");
					int qty = readIntInput();
					cartService.addToCart(customer.getCustomerId(), pid, qty);
					System.out.println("Added to cart.");
					break;
				case 4:
					List<CartItem> cart = cartService.getCart(customer.getCustomerId());
					if (cart.isEmpty()) {
						System.out.println("Cart is empty.");
					} else {
						cart.forEach((a) -> System.out.println(a));
						double total = cartService.calculateCartTotal(customer.getCustomerId());
						System.out.printf("Total Cart Value: " + total);
					}
					break;
				case 5:
					System.out.print("Enter Product ID to remove: ");
					int remPid = readIntInput();
					cartService.removeFromCart(customer.getCustomerId(), remPid);
					System.out.println("Item removed from cart.");
					break;
				case 6:
					Order order = orderService.placeOrder(customer.getCustomerId());
					System.out.println("Order placed successfully.");
					System.out.println("Reciept genereting .......");
					Thread.sleep(2000);
					printOrderReceipt(order, customer);
					break;
				case 7:
					List<Order> orders = orderService.getCustomerOrders(customer.getCustomerId());
					if (orders.isEmpty()) {
						System.out.println("No previous orders found.");
					} else {
						orders.forEach(System.out::println);
					}
					break;
				case 8:
					System.out.print("Enter Order ID to cancel: ");
					int oid = readIntInput();
					orderService.cancelOrder(oid, customer.getCustomerId());
					System.out.println("Order " + oid + " has been cancelled.");
					break;
				case 9:
				    System.out.print("Enter new address: ");
				    String newAddress = scanner.nextLine();
				    customerService.updateCustomerAddress(customer.getCustomerId(), newAddress);
				    System.out.println("Address updated successfully.");
				    break;
				case 10:
				    System.out.print("Enter Product ID: ");
				    int incId = readIntInput();
				    System.out.print("Enter quantity to add: ");
				    int addQty = readIntInput();
				    cartService.increaseQuantity(customer.getCustomerId(), incId, addQty);
				    System.out.println("Quantity increased.");
				    break;
				case 11:
				    System.out.print("Enter Product ID: ");
				    int decId = readIntInput();
				    System.out.print("Enter quantity to remove: ");
				    int subQty = readIntInput();
				    cartService.decreaseQuantity(customer.getCustomerId(), decId, subQty);
				    System.out.println("Quantity decreased.");
				    break;
				case 12:
				    logout = true;
				    break;	
				    default:
					System.out.println("Invalid selection.");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

// search product 
	private static void handleSearchProduct() {
		System.out.println("\nSearch by: 1. Name | 2. Category | 3. Brand | 4. Price Range");
		int option = readIntInput();
		switch (option) {
		case 1:
			System.out.print("Enter product name: ");
			String name = scanner.nextLine();
			productService.searchByName(name).forEach(System.out::println);
			break;
		case 2:
			System.out.print("Enter category: ");
			String cat = scanner.nextLine();
			productService.searchByCategory(cat).forEach(System.out::println);
			break;
		case 3:
			System.out.print("Enter brand: ");
			String brand = scanner.nextLine();
			productService.searchByBrand(brand).forEach(System.out::println);
			break;
		case 4:
			System.out.print("Enter min price: ");
			double min = readDoubleInput();
			System.out.print("Enter max price: ");
			double max = readDoubleInput();
			productService.filterByPriceRange(min, max).forEach(System.out::println);
			break;
		default:
			System.out.println("Invalid search option.");
		}
	}

	private static int readIntInput() {
		while (true) {
			try {
				String line = scanner.nextLine();
				return Integer.parseInt(line.trim());
			} catch (NumberFormatException e) {
				System.out.print("Invalid integer. Enter again: ");
			}
		}
	}

	private static double readDoubleInput() {
		while (true) {
			try {
				String line = scanner.nextLine();
				return Double.parseDouble(line.trim());
			} catch (NumberFormatException e) {
				System.out.print("Invalid number. Enter again: ");
			}
		}
	}
//order reciept
	private static void printOrderReceipt(Order order, Customer customer) {
		System.out.println();
		System.out.println("========================================");
		System.out.println("             ORDER RECEIPT              ");
		System.out.println("========================================");
		System.out.println("Order ID      : " + order.getOrderId());
		System.out.println("Customer ID   : " + customer.getCustomerId());
		System.out.println("Customer Name : " + customer.getName());
		System.out.println("Email         : " + customer.getEmail());
		System.out.println("Mobile        : " + customer.getMobile());
		System.out.println("Address       : " + customer.getAddress());
		System.out.println("Order Date    : " + order.getOrderDate());
		System.out.println("Order Status  : " + order.getOrderStatus());
		System.out.println("----------------------------------------");
		System.out.println("Items:");
		for (CartItem item : order.getItems()) {
			System.out.println(item.getProductName() + " | Price: " + item.getPrice() + " | Quantity: "
					+ item.getQuantity() + " | Total: " + item.getTotalPrice());
		}
		System.out.println("----------------------------------------");
		System.out.println("TOTAL AMOUNT  : " + order.getTotalAmount());
		System.out.println("========================================");
		System.out.println("       Thank you for your order!        ");
		System.out.println("========================================");
	}
	// guest must login (or register) before the order is placed
	private static boolean guestPlaceOrder() throws InterruptedException {
	    if (cartService.getCart(GUEST_ID).isEmpty()) {
	        System.out.println("Cart is empty. Add products first.");
	        return false;
	    }

	    System.out.println("\nLogin is required to place your order.");
	    Customer customer = null;

	    // keep asking until login succeeds or the guest cancels
	    while (customer == null) {
	        System.out.println("1. Login | 2. Register | 3. Cancel");
	        System.out.print("Enter choice: ");
	        int option = readIntInput();

	        if (option == 3) {
	            return false;
	        }
	        if (option != 1 && option != 2) {
	            System.out.println("Invalid selection.");
	            continue;
	        }
	        try {
	            if (option == 2) {
	                registerCustomerUI();
	            }
	            System.out.print("Enter Customer ID to Login: ");
	            customer = customerService.getCustomerById(readIntInput());
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // login completed: move guest cart items to this customer's cart
	    cartService.moveCart(GUEST_ID, customer.getCustomerId());
	    System.out.println("Welcome, " + customer.getName() + ". Your guest cart has been moved to your account.");

	    // place the order with the normal flow
	    Order order = orderService.placeOrder(customer.getCustomerId());
	    System.out.println("Order placed successfully.");
	    System.out.println("Receipt generating...");
	    Thread.sleep(2000);
	    printOrderReceipt(order, customer);
	    return true;
	}
}