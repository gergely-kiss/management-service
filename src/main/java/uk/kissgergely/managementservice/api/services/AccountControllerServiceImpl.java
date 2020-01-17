package uk.kissgergely.managementservice.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.vos.AccountVO;

@Service
public class AccountControllerServiceImpl implements AccountControllerService{
	
	AccountService accountService;
	
	
	@Autowired
	public AccountControllerServiceImpl(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public List<AccountVO> getAllAccounts() {
		
		return null;
	}

	@Override
	public AccountVO getAccountById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountVO createAccount(AccountVO account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountVO updateAccount(AccountVO account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountVO deleteAccount(AccountVO account) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private AccountEntity converter(AccountVO account) {
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setAccountName(account.getAccountName());
		accountEntity.setDescription(account.getAccountDescription());
		accountEntity.setHostReference(account.getAccountId());
		return accountEntity;
		
	}
	private AccountVO converter (AccountEntity accountEntity) {
		AccountVO account = new AccountVO();
		return account;
	}
}
