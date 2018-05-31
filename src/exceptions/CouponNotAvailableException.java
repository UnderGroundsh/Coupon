package exceptions;

import java.sql.SQLException;

import bean.Coupon;

// exception may occur when trying to get a specific coupon which is not in the DB

public class CouponNotAvailableException extends SQLException
 
{
	private Coupon coupon;
	
	public CouponNotAvailableException(Coupon coupon)
	{
		this.coupon = coupon;
	}
	
	public String getMessage()
	{
		return "Coupon "+coupon.getTitle()+" not available !";
	}
}
