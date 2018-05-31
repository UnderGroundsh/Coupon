package exceptions;

import java.sql.SQLException;

import bean.Coupon;

//exception may occur when trying to remove a specific coupon from DB

public class CouponRemovalException extends SQLException {
	private Coupon coupon;
	
	public CouponRemovalException(Coupon coupon)
	{
		this.coupon = coupon;
	}
	
	public String getMessage()
	{
		return "Removing Coupon "+coupon.getTitle()+" failed !";
	}
}
