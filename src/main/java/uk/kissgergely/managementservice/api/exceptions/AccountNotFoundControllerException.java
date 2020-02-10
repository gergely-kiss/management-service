package uk.kissgergely.managementservice.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotFoundControllerException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881947113520751311L;



	public AccountNotFoundControllerException(HttpStatus httpStatus, String exceptionMSG) {
		super(httpStatus, exceptionMSG);
	}

}
