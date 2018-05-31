package DAO;

import java.lang.Exception;
import java.util.Collection;
import java.util.List;
import exceptions.*;
import bean.Company;
import bean.Coupon;
import bean.Customer;


public interface CustomerDaoInterface {
            
			public Customer getCustomer(long Id) throws CustomerUpdateException, DBErrorException, InterruptedThreadException;
			public void updateCustomer(Customer customer) throws CustomerUpdateException, InterruptedThreadException;
			public void removeCustomer(Customer customer) throws CustomerRemovalException, InterruptedThreadException;
			public Collection <Customer> getAllCustomers() throws DBErrorException, InterruptedThreadException;
			public Collection<Coupon> getAllCoupons(Customer customer) throws DBErrorException, InterruptedThreadException;
			public void createCustomer(Customer customer) throws CustomerCreationException, DBErrorException, InterruptedThreadException;
			public boolean login(String customerName,String password) throws BadLoginException, InterruptedThreadException;
			public void addCouponToCustomer(Coupon coupon, Customer customer) throws DBErrorException, InterruptedThreadException;
			

			

	
}
