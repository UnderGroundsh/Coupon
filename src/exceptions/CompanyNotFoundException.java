package exceptions;

import bean.Company;

//  there is no access to non existed companies
public class CompanyNotFoundException extends Exception {

private Company company;
	
	public CompanyNotFoundException(Company company) {
		this.company = company;
	}
	
	@Override
	public String getMessage() {
		
		return "Company "+this.company.getName()+" wasn't found !";
	}
}
