package bean;

import java.util.Collection;

import exceptions.BadLoginException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CustomerFacade;
import systems.CouponSystem;


public class Customer {
    private long id;
    private String customerName;
    private String password;
    private Collection<Coupon> coupons;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Coupon> getCoupons() throws DBErrorException, InterruptedThreadException, BadLoginException {
        CustomerFacade c = (CustomerFacade)CouponSystem.getInstance().login(this.customerName, this.password, ClientType.CUSTOMER);
        return c.getAllPurchasedCoupons();
    }

 

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
