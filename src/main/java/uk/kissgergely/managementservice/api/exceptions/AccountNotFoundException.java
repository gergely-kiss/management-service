package uk.kissgergely.managementservice.api.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends AccountControllerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881947113520751311L;



	public AccountNotFoundException(HttpStatus httpStatus, String exceptionMSG) {
		super(httpStatus, exceptionMSG);
	}

}
