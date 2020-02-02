package uk.kissgergely.managementservice.api.services;

import java.util.List;

import uk.kissgergely.managementservice.api.exceptions.AccountAlreadyExistControllerException;
import uk.kissgergely.managementservice.api.exceptions.AccountNotFoundControllerException;
import uk.kissgergely.managementservice.vos.AccountVO;

public interface AccountControllerService {

	List<AccountVO> getAllAccounts() throws AccountNotFoundControllerException;

	AccountVO getAccountById(String id) throws AccountNotFoundControllerException;

	AccountVO createAccount(AccountVO account) throws AccountAlreadyExistControllerException;

	AccountVO updateAccount(AccountVO account) throws AccountNotFoundControllerException, AccountAlreadyExistControllerException;

	String deleteAccount(String hostReference) throws AccountNotFoundControllerException;

}
