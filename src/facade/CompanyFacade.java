package facade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import DAO.CompanyDAO;
import DAO.CompanyDBDAO;
import DAO.CouponDaoInterface;
import bean.Company;
import bean.Coupon;
import bean.CouponType;
import exceptions.BadLoginException;
import exceptions.CouponCreationException;
import exceptions.CouponRemovalException;
import exceptions.CouponUpdateException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

//
// Company Facade provides the business logic for companies
// DAO's exists for Companies/Coupons operations
//
public class CompanyFacade implements CouponClientFacade {
	private CompanyDAO companyDao;
	private CouponDaoInterface couponDao;
	private Company company;

	// returns the company
	public Company getCompany() {
		return this.company;
	}

	// set the Company DAO
	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	// set the Coupon DAO
	public void setCouponDao(CouponDaoInterface couponDao) {
		this.couponDao = couponDao;
	}

	public CompanyFacade() {
	}

	@Override

	public CouponClientFacade login(String name, String password, ClientType clientType) throws BadLoginException, DBErrorException , InterruptedThreadException{
		boolean login = this.companyDao.login(name, password);
		if (login) {
			long id = ((CompanyDBDAO) this.companyDao).getIdByName(name);
			Company company = this.companyDao.getCompany(id);
			this.company = company;
		}
		return this;
	}

	public void createCoupon(Coupon coupon) throws CouponCreationException, InterruptedThreadException {
		couponDao.createCoupon(coupon);
		
	}

	public void removeCoupon(Coupon coupon) throws CouponRemovalException , InterruptedThreadException{
		couponDao.removeCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) throws CouponUpdateException, InterruptedThreadException{
		
		couponDao.updateCoupon(coupon);
	}

	// get all coupons of that company
	public Collection<Coupon> getAllCoupons(long companyId) throws DBErrorException, InterruptedThreadException {
		return companyDao.getCoupons(companyId);
	}

	// get all and remove if coupon type is not the requested type
	public Coupon getCoupon(long id) throws DBErrorException, InterruptedThreadException {
		return couponDao.getCoupon(id);
	}

	/*
	 * public Collection<Coupon> getCouponById(long id) { Collection<Coupon>
	 * myCoupon = this.getAllCoupons(); for (Iterator<Coupon> iterator =
	 * myCoupon.iterator(); iterator .hasNext();) { Coupon coupon = iterator.next();
	 * if (coupon.getId() != id) { iterator.remove(); } } return myCoupon;
	 * 
	 * }
	 */

	// get all and remove if coupon type is not the requested type
	public Collection<Coupon> getAllCouponsByType(CouponType couponType, long id) throws DBErrorException, InterruptedThreadException {
		Collection<Coupon> allCoupons = this.getAllCoupons(id);
		for (Iterator<Coupon> iterator = allCoupons.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getType() != couponType) {
				iterator.remove();
			}
		}
		return allCoupons;
	}

	// get all and remove if price is higher than requested
	public Collection<Coupon> getAllCouponsByMaxPrice(double price, long id) throws DBErrorException, InterruptedThreadException {
		Collection<Coupon> allCoupons = this.getAllCoupons(id);
		for (Iterator<Coupon> iterator = allCoupons.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			if (coupon.getPrice() > price) {
				iterator.remove();
			}
		}
		return allCoupons;
	}

	// get all and remove if end date is greater than requested
	public Collection<Coupon> getAllCouponsByMaxDate(String date, long id) throws DBErrorException, InterruptedThreadException {
		Collection<Coupon> allCoupons = this.getAllCoupons(id);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = null;
		try {
			utilDate = df.parse(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		java.sql.Date maxDate = sqlDate;

		{
			for (Iterator<Coupon> iterator = allCoupons.iterator(); iterator.hasNext();) {
				Coupon coupon = iterator.next();
				if (coupon.getEndDate().after(maxDate)) {
					iterator.remove();
				}
			}
			return allCoupons;
		}

	}

}
