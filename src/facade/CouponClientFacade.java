package facade;

import exceptions.BadLoginException;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;

//
// common interface that provides login check functionality
// the concrete classes ( all Facade's ) must implement this method
//
public interface CouponClientFacade  {
	// determine which login type (Admin/user/company)
	// to return the correct Facade
	public CouponClientFacade login(String name, String password, ClientType clientType) throws DBErrorException, BadLoginException, InterruptedThreadException;
}
