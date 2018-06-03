package DAO;

import bean.Company;
import bean.Coupon;
import exceptions.BadLoginException;
import exceptions.CompanyCreationException;
import exceptions.CompanyRemovalException;
import exceptions.CompanyUpdateException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

import java.util.Collection;


public interface CompanyDAO {
    void createCompany(Company company) throws CompanyCreationException, InterruptedThreadException;
    void removeCompany(Company company) throws CompanyRemovalException, InterruptedThreadException, DBErrorException;
    void updateCompany(Company company) throws CompanyUpdateException, InterruptedThreadException;
    Company getCompany(long id) throws DBErrorException, InterruptedThreadException;
    Collection<Company> getCompanies() throws DBErrorException, InterruptedThreadException;
    Collection<Coupon> getCoupons(long companyId) throws DBErrorException, InterruptedThreadException;
    boolean login(String compName, String password) throws BadLoginException, DBErrorException, InterruptedThreadException;
}
