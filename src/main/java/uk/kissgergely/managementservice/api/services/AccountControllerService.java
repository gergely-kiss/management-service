package uk.kissgergely.managementservice.api.services;

import java.util.List;

import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.vos.AccountVO;

public interface AccountControllerService {

	List<AccountVO> getAllAccounts();

	AccountVO getAccountById(String id) throws AccountControllerException;

	AccountVO createAccount(AccountVO account) throws AccountControllerException;

	AccountVO updateAccount(AccountVO account) throws AccountControllerException;

	String deleteAccount(String hostReference) throws AccountControllerException;

}
