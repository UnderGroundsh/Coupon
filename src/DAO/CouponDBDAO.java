package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import exceptions.*;
import connection.ConnectionPool;
import bean.Coupon;
import bean.CouponType;

public class CouponDBDAO implements CouponDaoInterface{

	
	//creating new coupon and implementing it into the data base
	public void createCoupon(Coupon coupon) throws CouponCreationException, InterruptedThreadException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery ="INSERT INTO APP.COUPON (TITLE, START_DATE, END_DATE, AMOUNT,MESSAGE, TYPE, PRICE, IMAGE,COMPANY_ID) VALUES(?, ? , ?, ?, ?, ?, ?, ?,?)";
            preparedStatement= connection.prepareStatement(sqlQuery);
            //preparedStatement.setLong(1, coupon.getId());
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setDate(2, coupon.getStartDate());
            preparedStatement.setDate(3, coupon.getEndDate());
            preparedStatement.setInt(4, coupon.getAmount());
            preparedStatement.setString(5, coupon.getMessage());
            preparedStatement.setString(6, coupon.getType().toString());
            preparedStatement.setDouble(7, coupon.getPrice());
            preparedStatement.setString(8, coupon.getImage());
            preparedStatement.setLong(9, coupon.getByCompanyId());
            preparedStatement.executeUpdate();
            connection.commit();
		}catch(SQLException e){
		throw new CouponCreationException(coupon);
		
	}finally {
		if(connection!=null)
		ConnectionPool.getInstance().returnConnection(connection);			
	}
}
	
	
	//gating all coupons that there is
	@Override
	public Collection<Coupon> getAllCoupons() throws DBErrorException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
	List<Coupon> coupons =  new ArrayList<Coupon>();
	try {
		connection = ConnectionPool.getInstance().getConnection();
		String sqlQuery = "SELECT * FROM APP.COUPON";
		preparedStatement = connection.prepareStatement(sqlQuery);
		resultSet = preparedStatement.executeQuery();
		 while(resultSet.next()) {
			 	Coupon coupon = new Coupon();
			    coupon.setId(resultSet.getInt("ID"));
		        coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getInt("PRICE"));
		        coupon.setImage(resultSet.getString("IMAGE"));
		        coupon.setByCompanyId(resultSet.getLong("COMPANY_ID"));
		        
			    coupons.add(coupon);
		 }
	}catch(SQLException e) {
		throw new DBErrorException();
	}finally {
		if(connection!=null)
		ConnectionPool.getInstance().returnConnection(connection);
	}
	return coupons;
	}
	
	
	
	
	//Getting a coupon by ID
	@Override
	public Coupon getCoupon(long ID) throws DBErrorException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = new Coupon();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery= "SELECT * FROM APP.COUPON WHERE ID=?";
			preparedStatement = connection.prepareStatement (sqlQuery);
			preparedStatement.setLong(1, ID);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {
				return null;
			}else {
				coupon.setId(resultSet.getInt("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getInt("PRICE"));
		        coupon.setImage(resultSet.getString("IMAGE"));	
			    coupon.setByCompanyId(resultSet.getLong("COMPANY_ID"));
			  
			}
		}catch(SQLException e) { 
			throw new DBErrorException();
			
		}finally{
			if(connection!=null)
				ConnectionPool.getInstance().returnConnection(connection);
		}
			
			return coupon;
		}
	




//will  update coupons in the system
	@Override
	public void updateCoupon(Coupon coupon) throws CouponUpdateException, InterruptedThreadException {
	 Connection connection = null;
	 PreparedStatement preparedStatement = null;
	 try {
		 connection = ConnectionPool.getInstance().getConnection();
		 String sqlQuery = "UPDATE APP.COUPON SET TITLE = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?,MESSAGE=?, PRICE = ?, IMAGE = ?, COMPANY_ID = ? WHERE ID = ?";
		    preparedStatement= connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, coupon.getTitle());
            preparedStatement.setDate(2,coupon.getStartDate());
            preparedStatement.setDate(3,coupon.getEndDate());
            preparedStatement.setInt(4, coupon.getAmount());
            preparedStatement.setString(5, coupon.getType().toString());
            preparedStatement.setString(6, coupon.getMessage());
            preparedStatement.setDouble(7, coupon.getPrice());
            preparedStatement.setString(8, coupon.getImage());
            preparedStatement.setLong(9, coupon.getByCompanyId() );
            preparedStatement.setLong(10, coupon.getId());
            preparedStatement.executeUpdate();
		
	}catch(SQLException e) {
		throw new CouponUpdateException(coupon);
	}finally {
		if(connection!=null)
		 ConnectionPool.getInstance().returnConnection(connection);
	}
	}

	
	//will delete chosen coupon from the system
	@Override
	public void removeCoupon(Coupon coupon) throws CouponRemovalException, InterruptedThreadException {
     Connection connection= null;
     PreparedStatement preparedStatement = null;
     try {
    	 connection = ConnectionPool.getInstance().getConnection();
    	 String sqlQuery = " DELETE FROM APP.CUSTOMER_COUPON WHERE COUPON_ID = ?";
    	 preparedStatement = connection.prepareStatement(sqlQuery);
    	 preparedStatement.setLong(1, coupon.getId());
    	 preparedStatement.executeUpdate();
         sqlQuery = " DELETE FROM APP.COUPON WHERE ID = ?";
    	 preparedStatement = connection.prepareStatement(sqlQuery);
    	 preparedStatement.setLong(1, coupon.getId());
    	 preparedStatement.executeUpdate();
     }catch(SQLException e) {
    	throw new CouponRemovalException(coupon);
     }finally {
    	 if(connection!=null)
    	 ConnectionPool.getInstance().returnConnection(connection);
     }
     
	}
	
	
    //will show all company's coupons by ID
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws DBErrorException, InterruptedThreadException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = new Coupon();
		
	List<Coupon> coupons =  new ArrayList<Coupon>();
	try {
		connection = ConnectionPool.getInstance().getConnection();
		String sqlQuery = "SELECT * FROM COUPON";
		preparedStatement = connection.prepareStatement(sqlQuery);
		resultSet = preparedStatement.executeQuery();
		 while(resultSet.next()) {
			    coupon.setByCompanyId(resultSet.getLong("COMPANY_ID"));
			    coupon.setId(resultSet.getInt("ID"));
		        coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getInt("PRICE"));
		        coupon.setImage(resultSet.getString("IMAGE"));	
			    coupons.add(coupon);
		 }
	}
	catch(SQLException e) {
		throw new DBErrorException();
	}finally {
		if(connection!=null)
		ConnectionPool.getInstance().returnConnection(connection);
	}
	return coupons;
	}


}
