package uk.kissgergely.managementservice.api.exceptions;

public class AccountControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountControllerException(String exceptionMSG) {
		super(exceptionMSG);
	}
}
