package exceptions;

import java.sql.SQLException;

import bean.Customer;

// exception may occur when trying to create a new customer

public class CustomerCreationException extends SQLException 
{
	private Customer customer;
	
	public CustomerCreationException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Creating customer "+this.customer.getCustomerName()+" failed !";
	}
}
