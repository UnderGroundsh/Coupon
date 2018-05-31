package Utils;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DAO.CouponDBDAO;
import DAO.CouponDaoInterface;
import bean.Coupon;
import exceptions.CouponRemovalException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

public class DailyCouponExpirationTask {
	private CouponDaoInterface dao;
	private Boolean quit;
	private final ScheduledExecutorService scheduler;
	private final Runnable task;
	
	public DailyCouponExpirationTask(){
		this.quit = false;
		this.dao = new CouponDBDAO();
		this.task = new Runnable() {
			@Override
			public void run() {
				Collection<Coupon> coupons = null;
				try {
					coupons = dao.getAllCoupons();
				} catch (DBErrorException | InterruptedThreadException e1) {
					e1.getMessage();
				}
				for(Coupon coupon : coupons) {
					Date endDate = coupon.getEndDate();
					Date now = new Date();
					if(now.after(endDate)){
						try {
							dao.removeCoupon(coupon);
						} catch (CouponRemovalException | InterruptedThreadException e) {
							e.getMessage();
						}
					}
				}
			}
		};
		
		this. scheduler = Executors.newScheduledThreadPool(1);
	}
	
	public void run(){
		 scheduler.scheduleAtFixedRate(task, 5, 5, TimeUnit.SECONDS);
	}
	
	public void stop(){
		scheduler.shutdown();
	}
}
