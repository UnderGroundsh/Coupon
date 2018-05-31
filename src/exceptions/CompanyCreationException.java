package exceptions;

import java.sql.SQLException;

import org.apache.derby.client.am.SqlException;

import bean.Company;

//
// exception handler for an exception which may occur during company object creation
// and it's DB registration
//
public class CompanyCreationException extends SQLException {
	private Company company;
	
	public CompanyCreationException(Company company) {
		
		this.company = company;
	}
	
	@Override
	public String getMessage() {
		
		return "Creating company "+this.company.getName()+" failed !";
	}
}
