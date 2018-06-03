package facade;



import bean.*;
import exceptions.BadLoginException;
import exceptions.CompanyCreationException;
import exceptions.CompanyRemovalException;
import exceptions.CompanyUpdateException;
import exceptions.CustomerCreationException;
import exceptions.CustomerRemovalException;
import exceptions.CustomerUpdateException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

import java.util.Collection;

import DAO.*;



//
// Admin Facade provides the business functionality for sys admin
// this Facade refers to all 3 DAO's for all kind of operations
//
public class AdminFacade implements CouponClientFacade {
	private CustomerDaoInterface customerDao;
	private CompanyDAO companyDao;
	private CouponDaoInterface couponDao;

	
	
	
	public AdminFacade() {
	}
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws BadLoginException {
		if (name.equals("admin") && password.equals("1234")) {
			CompanyDAO company = new CompanyDBDAO();
			CustomerDaoInterface customer = new CustomerDBDAO();
			CouponDaoInterface coupon = new CouponDBDAO();
			return this;
		}
		throw new BadLoginException(); //: TODO change to right exception
	}
	
	// C.R.U.D operations may throws exceptions
	//
	public void createCompany(Company company) throws CompanyCreationException, InterruptedThreadException, DBErrorException  {
		
		companyDao.createCompany(company);
		
	}
	public void removeCompany(Company company) throws CompanyRemovalException, InterruptedThreadException, DBErrorException {
		companyDao.removeCompany(company);
		
	}
	public void updateCompany(Company company) throws CompanyUpdateException, InterruptedThreadException {
		companyDao.updateCompany(company);
		
	}
	public Collection<Company> getAllCompanies() throws DBErrorException, InterruptedThreadException{
	
		return companyDao.getCompanies();
	}

	// helper methods to ask DAO for the ID of a given name, then use the ID to get from DB 
/*	public Company getCompany(String compName) {
		long companyId = companyDao.getCompany(compName);
		Company company = new Company();
		company = companyDao.getCompany(companyId);
		
		return company;
		
	}
	*/
	
	
	// helper methods to ask DAO for the company by ID 
		public Company getCompanyByID(long companyId) throws DBErrorException, InterruptedThreadException{
			
			return companyDao.getCompany(companyId);
		}

	// updating a customer
	public void updateCustomer(Customer customer) throws CustomerUpdateException, InterruptedThreadException{
		customerDao.updateCustomer (customer);
		
	}
	
	// get collection of all customers
	public Collection<Customer> getAllCustomers() throws DBErrorException, InterruptedThreadException {
		Collection<Customer> allCustomers = customerDao.getAllCustomers();
		return allCustomers;
	}
	
	// create a new customer
	public void createCustomer(Customer customer) throws CustomerCreationException, DBErrorException, InterruptedThreadException {
		customerDao.createCustomer(customer);
		
	}

	// remove a customer
	public void removeCustomer(Customer customer) throws CustomerRemovalException, InterruptedThreadException{
		customerDao.removeCustomer(customer);
		
	}
	
	
	// helper methods to ask DAO for the customer by ID
		public Customer getCustomer(long customerId) throws CustomerUpdateException, DBErrorException, InterruptedThreadException{
			return customerDao.getCustomer(customerId);
		}

		public void setCompanyDBAO(CompanyDAO company) {
			this.companyDao = company;
		}

		public void setCustomerDAO(CustomerDaoInterface customer) {
			this.customerDao = customer;
		}

		public void setCouponDAO(CouponDaoInterface coupon) {
			this.couponDao = coupon;
		}
}
