package uk.kissgergely.managementservice.services.exceptions;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4894508808873558462L;

	public ServiceException(String exceptionMSG) {
		super(exceptionMSG);
	}
	
}
