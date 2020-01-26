package uk.kissgergely.managementservice.services.exceptions;

public class AccountNotFoundException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084252124483458775L;

	public AccountNotFoundException(String exceptionMSG) {
		super(exceptionMSG);
	}

}
