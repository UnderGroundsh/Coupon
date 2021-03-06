package bean;

import java.sql.Date;


public class Coupon {
    private long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private int amount;
    private CouponType type;
    private String message;
    private double price;
    private String image;
    private long byCompanyId; 

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    

    public long getByCompanyId() {
		return byCompanyId;
	}

	public void setByCompanyId(long byCompanyId) {
		this.byCompanyId = byCompanyId;
	}

	@Override
	public boolean equals(Object c) {
		Coupon coup = (Coupon)c;
		return coup.getId() == this.getId();
	}
	
	@Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", byCompany='" + byCompanyId + '\'' +
                '}';
    }
}
