package DAO;

import java.sql.Connection;
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
import bean.Customer;
public class CustomerDBDAO implements CustomerDaoInterface {
	
	
	//create new customer in the data base
	public void createCustomer(Customer customer) throws CustomerCreationException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = "INSERT INTO APP.CUSTOMER (CUST_NAME,PASSWORD) VALUES ( ? ,?)";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setString(1, customer.getCustomerName());
			//preparedStatement.setLong(3, customer.getId());
			preparedStatement.executeUpdate();
			
		} catch(SQLException e) {
			throw new CustomerCreationException(customer);
		} finally {
			if (connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}
	
	
	//will get all customer from the data base
	public Collection<Customer> getAllCustomers() throws DBErrorException, InterruptedThreadException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		
		List<Customer>customers = new ArrayList<Customer>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = "SELECT * FROM APP.CUSTOMER";
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customer customer = new Customer();
				customer.setPassword(resultSet.getString("PASSWORD"));
				customer.setCustomerName(resultSet.getString("CUST_NAME"));
				customer.setId(resultSet.getInt("ID"));	
				customers.add(customer);
			}
		}catch(SQLException e) {
			throw new DBErrorException();
		}finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return customers;
	}
	
   //get the customer from the data base by a chosen ID
	@Override
	public Customer getCustomer(long Id) throws DBErrorException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = new Customer();
		
		try {
			connection = ConnectionPool.getInstance().getConnection();
			
			String sqlQuery = "SELECT * FROM APP.CUSTOMER WHERE ID = ?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, Id);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {
				return null;
			} else {	
				customer.setPassword(resultSet.getString("PASSWORD"));
				customer.setCustomerName(resultSet.getString("CUST_NAME"));
				customer.setId(resultSet.getInt("ID"));	
			}
		}catch(SQLException e) {
			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return customer;
	}
		
	// will update customer that exist in the database
	@Override
	public void updateCustomer(Customer customer) throws CustomerUpdateException, InterruptedThreadException {
		 Connection connection = null;
	       PreparedStatement preparedStatement = null; 
	       try {
	    	   connection = ConnectionPool.getInstance().getConnection();
	    	   String sqlQuery = "UPDATE APP.CUSTOMER SET PASSWORD = ? , CUST_NAME = ?  WHERE ID =?";
	    	    preparedStatement = connection.prepareStatement(sqlQuery);
				preparedStatement.setString(1, customer.getPassword());
				preparedStatement.setString(2, customer.getCustomerName());
				preparedStatement.setLong(3, customer.getId());
				preparedStatement.executeUpdate();
	    	   
	       }catch(SQLException e){
	    	   throw new CustomerUpdateException(customer);
	       }finally {
	    	   if(connection!=null)
	    		ConnectionPool.getInstance().returnConnection(connection);
			}
	       
	       
		}
	
	//will delete a chosen customer by ID
	@Override
	public void removeCustomer(Customer customer) throws CustomerRemovalException, InterruptedThreadException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null; 
	    try {
	    	connection = ConnectionPool.getInstance().getConnection();
	    	String sqlQuery = " DELETE FROM APP.CUSTOMER WHERE ID=?";
	    	preparedStatement = connection.prepareStatement(sqlQuery);
	    	preparedStatement.setLong(1, customer.getId());
			preparedStatement.executeUpdate();
	    	
	    }catch(SQLException e){
	    	 throw new CustomerRemovalException(customer);  
	       }finally {
	    	   if(connection!=null)
	    	   ConnectionPool.getInstance().returnConnection(connection);
			}
	}
	@Override
	public boolean login(String compName, String password) throws BadLoginException, InterruptedThreadException {
		Long companyId = null;
		
		Connection connection=null;
			String query = "SELECT ID FROM APP.CUSTOMER WHERE CUST_NAME=? AND PASSWORD=?";
			PreparedStatement pstmt;
			try {
				connection = ConnectionPool.getInstance().getConnection();
				pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				companyId = rs.getLong(1);
			}
			}
			 catch (SQLException e) {
				
				throw new BadLoginException();
			}
	finally {
		if (connection!=null)			
		ConnectionPool.getInstance().returnConnection(connection);
	}
			if(companyId!=null)
				return true;
			return false;
			
			
	}

	
	public void addCouponToCustomer(Coupon coupon, Customer customer) throws DBErrorException, InterruptedThreadException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		try {
			String query = "INSERT INTO APP.CUSTOMER_COUPON (CUSTOMER_ID, COUPON_ID) VALUES (?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, customer.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}
	
	@Override
	public Collection<Coupon> getAllCoupons(Customer customer) throws DBErrorException, InterruptedThreadException {
		
		// construct a List<Coupon> to return the data
		List<Coupon> myCoupons = new ArrayList<Coupon>();
		Connection connection = ConnectionPool.getInstance().getConnection();
		try {
			String query = "SELECT CPN.* FROM APP.COUPON CPN "                     //cpn for coupons
					+ "JOIN APP.CUSTOMER_COUPON CC "
					+ "ON CPN.ID = CC.COUPON_ID " + "WHERE CC.CUSTOMER_ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, customer.getId());
			ResultSet rs = pstmt.executeQuery();
			Coupon coupon = null;
			while (rs.next()) {
				coupon = new Coupon();
				coupon.setId(rs.getInt("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getInt("PRICE"));
		        coupon.setImage(rs.getString("IMAGE"));	
			    coupon.setByCompanyId(rs.getLong("COMPANY_ID"));
				// add to list
				myCoupons.add(coupon);
			}
			pstmt.close();
			
		} catch (SQLException e) {
			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return myCoupons;
	}


	public long getIdByName(String name) throws DBErrorException, InterruptedThreadException {
		Long customerId = null;
		Connection connection = ConnectionPool.getInstance().getConnection();
		
			String query = "SELECT ID FROM APP.CUSTOMER WHERE CUST_NAME=?";
			PreparedStatement pstmt;
			try {
				pstmt = connection.prepareStatement(query);
			
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				customerId = rs.getLong(1);
			}
			}
			 catch (SQLException e) {
				
				throw new DBErrorException();
			}
	finally {
		if(connection!=null)
		ConnectionPool.getInstance().returnConnection(connection);
	}
			return customerId;
	}




}