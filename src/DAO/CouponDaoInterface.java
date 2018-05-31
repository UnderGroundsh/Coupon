package DAO;


import java.util.Collection;

import bean.Coupon;
import bean.CouponType;
import exceptions.CouponCreationException;
import exceptions.CouponRemovalException;
import exceptions.CouponUpdateException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

public interface CouponDaoInterface {

	  void createCoupon(Coupon coupon) throws CouponCreationException, InterruptedThreadException;
	    void removeCoupon(Coupon coupon) throws CouponRemovalException, InterruptedThreadException;
	    void updateCoupon(Coupon coupon) throws CouponUpdateException, InterruptedThreadException;
	    Coupon getCoupon(long id) throws DBErrorException, InterruptedThreadException;
	    Collection<Coupon> getAllCoupons() throws DBErrorException, InterruptedThreadException;
	    Collection<Coupon> getCouponsByType(CouponType couponType) throws DBErrorException, InterruptedThreadException;
	    
		
		
	
}
