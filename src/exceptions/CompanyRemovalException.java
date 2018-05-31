package exceptions;

import java.sql.SQLException;

import bean.Company;

// exception to indicate a company could not be removed

public class CompanyRemovalException extends SQLException {
	private Company company;

	public CompanyRemovalException(Company company) {
		this.company = company;
	}
	
	@Override
	public String getMessage() {
		
		return "Remove company "+this.company.getName()+" failed !";
	}
}
