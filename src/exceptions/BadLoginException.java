package exceptions;


public class BadLoginException extends Exception {

	
	public BadLoginException() {

	}
	
	@Override
	public String getMessage() {
		
		return "Username or Password is incorrect";
	}
}

