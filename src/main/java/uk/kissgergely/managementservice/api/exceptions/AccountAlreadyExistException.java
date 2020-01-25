package uk.kissgergely.managementservice.api.exceptions;

import org.springframework.http.HttpStatus;

public class AccountAlreadyExistException extends AccountControllerException{


	private static final long serialVersionUID = -2657249216646718365L;

	/**
	 * 
	 */

	public AccountAlreadyExistException(HttpStatus httpStatus, String exceptionMSG) {
		super(httpStatus, exceptionMSG);
		// TODO Auto-generated constructor stub
	}


}
