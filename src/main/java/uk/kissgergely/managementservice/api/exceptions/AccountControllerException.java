package uk.kissgergely.managementservice.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountControllerException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1936419339190175559L;

	public AccountControllerException(HttpStatus httpStatus, String exceptionMSG) {
		super(httpStatus, exceptionMSG);
	}
}
