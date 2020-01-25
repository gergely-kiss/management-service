package uk.kissgergely.managementservice.exceptions;

public class AccountAlreadyExistException extends AccountServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1114630632014378338L;

	public AccountAlreadyExistException(String exceptionMSG) {
		super(exceptionMSG);
	}


}
