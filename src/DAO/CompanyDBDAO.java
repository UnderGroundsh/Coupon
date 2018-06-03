package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.*;
import connection.ConnectionPool;
import bean.Company;
import bean.Coupon;
import bean.CouponType;

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool = ConnectionPool.getInstance();

	// will create new company and insert it into the data base
	public void createCompany(Company company) throws CompanyCreationException, InterruptedThreadException {

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = "INSERT INTO APP.COMPANY (EMAIL , PASSWORD , COMPANY_NAME ) values (? , ? ,?)";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, company.getEmail());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getName());
			// preparedStatement.setLong(4, company.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new CompanyCreationException(company);
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	// will update existing company in the system
	public void updateCompany(Company company) throws CompanyUpdateException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = "UPDATE APP.COMPANY SET EMAIL = ? , PASSWORD = ? , COMPANY_NAME=? WHERE ID =?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, company.getEmail());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getName());
			preparedStatement.setLong(4, company.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new CompanyUpdateException(company);
		}
		finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	// will delete company from the system
	public void removeCompany(Company company) throws CompanyRemovalException, InterruptedThreadException, DBErrorException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {	 		
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = " DELETE FROM APP.COUPON WHERE COMPANY_ID=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, company.getId());
			preparedStatement.executeUpdate();
			sqlQuery = " DELETE FROM APP.CUSTOMER_COUPON WHERE COMPANY_ID=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, company.getId());
			preparedStatement.executeUpdate();
			sqlQuery = " DELETE FROM APP.COMPANY WHERE ID=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, company.getId());
			preparedStatement.executeUpdate();
			

		} catch (SQLException e) {
			throw new CompanyRemovalException(company);
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	public Company getCompany(long ID) throws DBErrorException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = new Company();

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String sqlQuery = "SELECT * FROM APP.COMPANY WHERE ID = ?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, ID);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return null;
			} else {
				company.setEmail(resultSet.getString("EMAIL"));
				company.setPassword(resultSet.getString("PASSWORD"));
				company.setName(resultSet.getString("COMPANY_NAME"));
				company.setId(resultSet.getInt("ID"));
			}
		} catch (SQLException e) {
			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return company;
	}

	// will show all the companies that exist in the data base
	@Override
	public Collection<Company> getCompanies() throws DBErrorException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Company> companies = new ArrayList<Company>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = "SELECT * FROM APP.COMPANY";
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			Company company = null;

			while (resultSet.next()) {
				company = new Company();
				company.setEmail(resultSet.getString("EMAIL"));
				company.setPassword(resultSet.getString("PASSWORD"));
				company.setName(resultSet.getString("COMPANY_NAME"));
				company.setId(resultSet.getInt("ID"));
				companies.add(company);
				System.out.println(company);

			}
		} catch (SQLException e) {
			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return companies;
	}

	@Override
	public Collection<Coupon> getCoupons(long companyId) throws DBErrorException, InterruptedThreadException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlQuery = "SELECT * FROM APP.COUPON WHERE COMPANY_ID=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("type")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getInt("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));
				coupon.setByCompanyId(resultSet.getLong("COMPANY_ID"));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupons;
	}

	@Override
	public boolean login(String compName, String password) throws BadLoginException, DBErrorException, InterruptedThreadException {
		Long companyId = null;
		Connection connection = null;

		String query = "SELECT ID FROM APP.COMPANY WHERE COMPANY_NAME=? AND PASSWORD=?";
		PreparedStatement pstmt;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()==false)
			{
				throw new BadLoginException();
			}
				
			while (rs.next()) {
				companyId = rs.getLong(1);
			}
		} catch (SQLException e) {

			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		if (companyId != null)
			return true;
		return false;

	}

	public long getIdByName(String name) throws DBErrorException, InterruptedThreadException {
		Long companyId = null;
		Connection connection = ConnectionPool.getInstance().getConnection();

		String query = "SELECT ID FROM APP.COMPANY WHERE COMPANY_NAME=?";
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				companyId = rs.getLong(1);
			}
		} catch (SQLException e) {

			throw new DBErrorException();
		} finally {
			if(connection!=null)
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return companyId;
	}
}
