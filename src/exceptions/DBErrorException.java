package exceptions;

import java.sql.SQLException;

public class DBErrorException extends SQLException {

	
	public DBErrorException() {

	}
	
	@Override
	public String getMessage() {
		
		return "There was a problem getting info from the DB";
	}
}


