package exceptions;

import java.sql.SQLException;

import bean.Customer;

// exception may occur when trying to remove a customer

public class CustomerRemovalException extends SQLException {
	private Customer customer;
	
	public CustomerRemovalException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Removing customer "+this.customer.getCustomerName()+" failed !";
	}
}
