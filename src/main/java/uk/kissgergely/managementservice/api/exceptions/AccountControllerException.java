package uk.kissgergely.managementservice.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Actor Not Found")
public class AccountControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountControllerException(String exceptionMSG) {
		super(exceptionMSG);
	}
}
