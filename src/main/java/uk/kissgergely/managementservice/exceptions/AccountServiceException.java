package uk.kissgergely.managementservice.exceptions;

public class AccountServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountServiceException(String exceptionMSG) {
		super(exceptionMSG);
	}
	
}
