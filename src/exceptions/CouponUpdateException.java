package exceptions;

import java.sql.SQLException;

import bean.Coupon;

//exception may occur when trying to update a specific coupon from DB

public class CouponUpdateException extends SQLException {
	private Coupon coupon;
	
	public CouponUpdateException(Coupon coupon)
	{
		this.coupon = coupon;
	}
	
	public String getMessage()
	{
		return "Updating Coupon "+coupon.getTitle()+" failed !";
	}
}
