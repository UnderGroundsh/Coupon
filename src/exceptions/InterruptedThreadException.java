package exceptions;

public class InterruptedThreadException extends InterruptedException {
	public InterruptedThreadException()
	{
	
	}
public String getMessage() {
		
		return "Thread got Interrupted";
	}

}
