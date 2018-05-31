package exceptions;

import java.sql.SQLException;

import bean.Company;

// exception to indicate a company could not be update

public class CompanyUpdateException extends SQLException {
	private Company company;
	
	public CompanyUpdateException(Company company) {
		this.company = company;
	}
	
	@Override
	public String getMessage() {

		return "Update for company "+this.company.getName()+" failed !";
	}
}
