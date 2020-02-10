package uk.kissgergely.managementservice.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountAlreadyExistControllerException extends ResponseStatusException {

	private static final long serialVersionUID = -2657249216646718365L;

	/**
	 * 
	 */

	public AccountAlreadyExistControllerException(HttpStatus httpStatus, String exceptionMSG) {
		super(httpStatus, exceptionMSG);
	}

}
