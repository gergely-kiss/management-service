package uk.kissgergely.managementservice.api.services;

import java.util.List;

import uk.kissgergely.managementservice.vos.AccountVO;

public interface AccountControllerService {

	List<AccountVO> getAllAccounts();

	AccountVO getAccountById(String id);

	AccountVO createAccount(AccountVO account);

	AccountVO updateAccount(AccountVO account);

	AccountVO deleteAccount(AccountVO account);

}
