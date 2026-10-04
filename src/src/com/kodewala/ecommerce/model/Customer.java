package src.com.kodewala.ecommerce.model;

import java.util.Objects;

public class Customer {
	private int customerId;
	private String name;
	private String email;
	private String mobile;
	private String address;

	public Customer() {
	}

	public Customer(int customerId, String name, String email, String mobile, String address) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (!(o instanceof Customer))
//			return false;
//		Customer customer = (Customer) o;
//		return customerId == customer.customerId;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(customerId);
//	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", address=" + address + "]";
	}

}