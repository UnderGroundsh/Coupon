package exceptions;

import java.sql.SQLException;

import bean.Customer;

// exception may occur when trying to update customer's details

public class CustomerUpdateException extends SQLException {
private Customer customer;
	
	public CustomerUpdateException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Updating customer "+this.customer.getCustomerName()+" failed !";
	}
}
