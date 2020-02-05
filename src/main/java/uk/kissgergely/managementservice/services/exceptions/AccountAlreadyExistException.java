package uk.kissgergely.managementservice.services.exceptions;

public class AccountAlreadyExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1114630632014378338L;

	public AccountAlreadyExistException(String exceptionMSG) {
		super(exceptionMSG);
	}


}
