package exceptions;

import java.sql.SQLException;

import bean.Customer;

// exception may occur when trying to get a customer which does not exists

public class CustomerNotFoundException extends SQLException {
	private Customer customer;
	
	public CustomerNotFoundException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Customer "+this.customer.getCustomerName()+" not found !";
	}
}
