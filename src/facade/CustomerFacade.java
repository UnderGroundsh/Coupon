package facade;

import java.util.*;


import DAO.*;
import bean.*;
import exceptions.BadLoginException;
import exceptions.CouponNotAvailableException;
import exceptions.CouponUpdateException;
import exceptions.CustomerUpdateException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;



//
// Customer Facade provides the business logic for customers
// DAO's exists for Customers/Coupons operations
//
public class CustomerFacade implements CouponClientFacade {
	private CustomerDaoInterface customerDao;
	private CouponDaoInterface couponDao;
	private Customer customer;

	// default public
	public CustomerFacade() {
	}

	// return this customer
	public Customer getCustomer() {
		return this.customer;
	}



	// set the Customer DAO
	public void setCustomerDao(CustomerDaoInterface customerDao) {
		this.customerDao = customerDao;
	}

	// set the Coupon DAO
	public void setCouponDao(CouponDaoInterface couponDao) {
		this.couponDao = couponDao;
	}

	@Override
	public CouponClientFacade login(String name, String password,	ClientType clientType) throws DBErrorException, BadLoginException , InterruptedThreadException{
		boolean login = this.customerDao.login(name, password);
		if (login) {
			long id = ((CustomerDBDAO) this.customerDao).getIdByName(name);
			Customer customer;
			try {
				customer = this.customerDao.getCustomer(id);
				this.customer = customer;
			} catch (CustomerUpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this;
	}


	
	public Coupon getCouponById(long id) throws DBErrorException, InterruptedThreadException{
		return couponDao.getCoupon(id);
	}




	// get all my Coupons
	public Collection<Coupon> getAllPurchasedCoupons() throws DBErrorException, InterruptedThreadException {
		return customerDao.getAllCoupons(this.getCustomer());
	}

	// get all and remove by comparing the CouponType
	// Iterator.remove is the only safe way to modify a collection during iteration
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws DBErrorException, InterruptedThreadException {
		Collection<Coupon> allMyCoupons = customerDao.getAllCoupons(this.customer);
		for (Iterator<Coupon> iterator = allMyCoupons.iterator(); iterator.hasNext();) {
		    Coupon coupon = iterator.next();
		    if (coupon.getType() != couponType){
		        iterator.remove();
		    }
		}
		return allMyCoupons;
	}

	// get all and remove if price is higher than requested
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws DBErrorException, InterruptedThreadException{
		Collection<Coupon> allMyCoupons = customerDao.getAllCoupons(this.customer);
		for (Iterator<Coupon> iterator = allMyCoupons.iterator(); iterator.hasNext();) {
		    Coupon coupon = iterator.next();
		    if (coupon.getPrice() > price){
		        iterator.remove();
		    }
		}
		return allMyCoupons;
	}

	// coupon i can buy (those who are not mine already ) and ( amount > 0 )
	public Collection<Coupon> getAllAvailableCoupons() throws DBErrorException, InterruptedThreadException{
		// getting all coupons in system
		Collection<Coupon> allCoupons = couponDao.getAllCoupons();
		// get  my coupons
		Collection<Coupon> myCoupons = customerDao.getAllCoupons(this.customer);
		// Subtract and store in a new var notMyCoupons
		allCoupons.removeAll(myCoupons);
		Collection<Coupon> notMyCoupons = allCoupons;
		for(Iterator<Coupon> iterator = notMyCoupons.iterator() ; iterator.hasNext() ;){
			Coupon coupon = iterator.next();
			// remove from list if out of stock
			if (coupon.getAmount() < 1){
				iterator.remove();
			}
		}
		return notMyCoupons;
	}

	// filter list of all available coupon to purchase
	public Collection<Coupon> getAllAvailableCouponsByType(CouponType couponType) throws DBErrorException, InterruptedThreadException{
		Collection<Coupon> allAvailableCoupons = getAllAvailableCoupons();
		// list to populate with the matching coupons
		for(Iterator<Coupon> iterator = allAvailableCoupons.iterator() ; iterator.hasNext() ;){
			Coupon coupon = iterator.next();
			if (coupon.getType() != couponType){
				iterator.remove();
			}
		}
		return allAvailableCoupons;
	}

	// filter list of all available coupon to purchase
	public Collection<Coupon> getAllAvailableCouponsByPrice(double price) throws DBErrorException, InterruptedThreadException {
		Collection<Coupon> allAvailableCoupons = getAllAvailableCoupons();
		// list to populate with the matching coupons
		for(Iterator<Coupon> iterator = allAvailableCoupons.iterator() ; iterator.hasNext() ;){
			Coupon coupon = iterator.next();
			if (coupon.getPrice() > price){
				iterator.remove();
			}
		}
		return allAvailableCoupons;
	}




	public void purchaseCoupon(Coupon coupon) throws CouponNotAvailableException, CouponUpdateException, DBErrorException , InterruptedThreadException {
		// getting realtime coupon amount from DB
		Coupon couponFromDB = couponDao.getCoupon(coupon.getId());
		System.out.println(couponFromDB);

		if (couponFromDB == null){
			throw new CouponNotAvailableException(coupon);
		}
		if (couponFromDB.getAmount() <= 0){
			
			throw new CouponNotAvailableException(coupon);
		}
		// and not purchased already
		if (getAllPurchasedCoupons().contains(couponFromDB))
		{
		
			throw new CouponNotAvailableException(coupon);
		}
		// purchase
		customerDao.addCouponToCustomer(couponFromDB, this.customer);
		
		// decrease amount
		couponFromDB.setAmount(couponFromDB.getAmount() - 1);
		couponDao.updateCoupon(couponFromDB);
		
	}
	
}
