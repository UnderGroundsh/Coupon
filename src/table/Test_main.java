package table;

import java.sql.Connection;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import connection.ConnectionPool;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;
import systems.CouponSystem;
import DAO.CompanyDAO;
import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CouponDaoInterface;
import DAO.CustomerDBDAO;
import DAO.CustomerDaoInterface;
import Utils.DailyCouponExpirationTask;
import bean.Company;
import bean.Coupon;
import bean.CouponType;
import bean.Customer;
import exceptions.BadLoginException;
import exceptions.CompanyCreationException;
import exceptions.CompanyRemovalException;
import exceptions.CouponNotAvailableException;
import exceptions.CouponUpdateException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

public class Test_main {
	@SuppressWarnings("deprecation")
	public static void main(String[]args) throws CouponNotAvailableException, CouponUpdateException, DBErrorException, InterruptedThreadException, BadLoginException, CompanyCreationException, CompanyRemovalException {
		CouponSystem couponSystem = CouponSystem.getInstance();
//	CouponClientFacade a = couponSystem.login("ronen", "PUBGNoob", ClientType.CUSTOMER);
//		System.out.println(a);
//		Coupon cop=new Coupon();
//		cop.setId(3);
//		cop.setTitle("pubg");
//		System.out.println(cop.getAmount());
		//((CustomerFacade)a).purchaseCoupon(cop);
		CouponClientFacade a = couponSystem.login("admin", "1234", ClientType.ADMIN);
		Company company=new Company();
		company.setEmail("asdasdaff@asd");
		company.setId(10);
		company.setName("test");
		company.setPassword("1234");
		((AdminFacade)a).createCompany(company);//create company-work
		//((AdminFacade)a).removeCompany(company);
		
		
		
		
		
		
		
//		ConnectionPool pool = ConnectionPool.getInstance();
//		List<Thread> threads = new ArrayList<>();
//		threads.add(new Thread(() -> {
//			Connection conn = pool.getConnection();
//			System.out.println("fun fun fun");
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			finally {
//				pool.returnConnection(conn);
//			}
//		}));
//		threads.forEach(th -> th.start());
		
//		CouponDBDAO dao = new CouponDBDAO();
		
//		DailyCouponExpirationTask daily = new DailyCouponExpirationTask();
//		daily.run();
//		while(true){
//			System.out.println(dao.getAllCoupons().size());
		
		
//		CompanyDAO dao = new CompanyDBDAO();
//		CustomerDaoInterface dao2 = new CustomerDBDAO();
//		CouponDaoInterface dao3 = new CouponDBDAO();
//		ConnectionPool pool=ConnectionPool.getInstance();
//		Connection conn=pool.getConnection();
//		AdminFacade adminFacade=new AdminFacade(dao2,dao,dao3);
//		Customer customer=new Customer();
//		CustomerFacade custFacade=new CustomerFacade(customer);
//		custFacade.setCustomerDao(dao2);
//		custFacade.setCouponDao(dao3);
		
		/*
		###company checks
		--------------------------
		Company company=new Company();
		company.setEmail("asdasdaff@asd");
		//company.setId(5);
		company.setName("test3");
		company.setPassword("1234");
		//adminFacade.createCompany(company);//create company-work
		//adminFacade.removeCompany(company);//remove company-work
		//company.setEmail("penTest");
		//company.setName("test");
		//company.setPassword("1234");
		//adminFacade.updateCompany(company);//update company-work
		//System.out.println(adminFacade.getCompanyByID(2));//print by ID-work
		System.out.println(adminFacade.getAllCompanies());//print all-work
		---------------------------
		###all works
		*/
		
		
		/*
		//###CUSTOMER CHECK
		-------------------
		customer.setId(4);
		customer.setCustomerName("ben");
		customer.setPassword("PUBGPro");
		//admin.createCustomer(customer);//create customer-work
		//admin.removeCustomer(customer);//remove customer-work
		//customer.setCustomerName("sean");
		//customer.setPassword("PUBGMPro");
		//admin.updateCustomer(customer);//update customer-work
		//System.out.println(admin.getCustomer(4));//print by id-work
		//System.out.println(admin.getAllCustomers());//print all-work
		------------------
		//###all works
		*/
//		Company company=new Company();
//		company.setId(4);
//		CompanyFacade Compfacade=new CompanyFacade(company);
//		Compfacade.setCompanyDao(dao);
//		Compfacade.setCouponDao(dao3);
		
		//###company facade utils
	    //-------------------------
//		java.util.Date d = Date.from( Instant.ofEpochSecond( 1525971247l ) );
//		java.sql.Date date = new Date(d.getTime());
//		System.out.println(date);
		
//Coupon cop=new Coupon();
//cop.setAmount(2);
//cop.setId(3);
//cop.setEndDate(new Date(117, 12, 12));
//cop.setImage("test2");
//cop.setMessage("test2");
//cop.setPrice(50.0);
//cop.setStartDate(new Date(117, 5, 5));
//cop.setTitle("pubg");
//cop.setType(CouponType.FOOD);
//cop.setByCompanyId(company.getId());
////Compfacade.createCoupon(cop);//create coupon-works
////Compfacade.removeCoupon(cop);//remove coupon-works
//Compfacade.updateCoupon(cop);//update coupon-works
////System.out.println(Compfacade.getAllCoupons(company.getId()));//print coupon of a company-works
////System.out.println(Compfacade.getAllCouponsByType(CouponType.FOOD,company.getId()));//print by type-works
////System.out.println(Compfacade.getAllCouponsByMaxDate("2018-01-10", company.getId()));//print by "till this date"-works
////System.out.println(Compfacade.getAllCouponsByMaxPrice(49.9, company.getId()));//print by price-works
// // ---------------------------------
// //###all works
//  
//		
//		
//		
////###customer Facade
//
	
//
//		custFacade.purchaseCoupon(cop);
//		
		
		




		
	
		
		
		
		
		
		
//		pool.returnConnection(conn);
		}
		
	}
