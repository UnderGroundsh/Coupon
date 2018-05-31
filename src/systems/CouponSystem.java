package systems;

import bean.Company;

import DAO.CompanyDAO;
import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CouponDaoInterface;
import DAO.CustomerDBDAO;
import DAO.CustomerDaoInterface;
import connection.ConnectionPool;
import exceptions.BadLoginException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {
	
	private static CouponSystem instance;
	
	private CouponSystem() {
		instance = this;
	}
	
	public static CouponSystem getInstance() {
		if (instance == null) {
			new CouponSystem();
		}
		return instance;
	}
	
	public CouponClientFacade login(String name, String password, ClientType type) throws DBErrorException, BadLoginException, InterruptedThreadException {
		CompanyDAO company = new CompanyDBDAO();
		CustomerDaoInterface customer = new CustomerDBDAO();
		CouponDaoInterface coupon = new CouponDBDAO();
		switch(type) {
		case ADMIN:
			AdminFacade admin = (AdminFacade) new AdminFacade();
			admin.setCompanyDBAO(company);
			admin.setCustomerDAO(customer);
			admin.setCouponDAO(coupon);
			return admin.login(name, password, type);
		case COMPANY:
			CompanyFacade companyFacade = (CompanyFacade) new CompanyFacade();
			companyFacade.setCompanyDao(company);
			companyFacade.setCouponDao(coupon);
			return companyFacade;
		case CUSTOMER:
			CustomerFacade customerFacade = (CustomerFacade) new CustomerFacade();
			customerFacade.setCustomerDao(customer);
			customerFacade.setCouponDao(coupon);
			return customerFacade.login(name, password, type);
		}
		Default: 
			return null;
	}
	
	public void shutdown() throws DBErrorException {
		ConnectionPool.getInstance().closeAllConnections();
		
	}
	

}
