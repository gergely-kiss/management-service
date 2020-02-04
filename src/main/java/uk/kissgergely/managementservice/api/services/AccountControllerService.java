package uk.kissgergely.managementservice.api.services;

import java.util.List;

import uk.kissgergely.managementservice.api.exceptions.AccountAlreadyExistControllerException;
import uk.kissgergely.managementservice.api.exceptions.AccountNotFoundControllerException;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;

public interface AccountControllerService {

	List<AccountResponse> getAllAccounts() throws AccountNotFoundControllerException;

	AccountResponse getAccountById(String id) throws AccountNotFoundControllerException;

	AccountResponse createAccount(String id, AccountRequest accountRequest) throws AccountAlreadyExistControllerException;

	AccountResponse updateAccount(String id, AccountRequest accountRequest) throws AccountNotFoundControllerException, AccountAlreadyExistControllerException;

	String deleteAccount(String hostReference) throws AccountNotFoundControllerException;

}
