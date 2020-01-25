package uk.kissgergely.managementservice.exceptions;

public class AccountNotFoundException extends AccountServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084252124483458775L;

	public AccountNotFoundException(String exceptionMSG) {
		super(exceptionMSG);
	}

}
