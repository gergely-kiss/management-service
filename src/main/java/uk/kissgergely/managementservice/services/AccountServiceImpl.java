package uk.kissgergely.managementservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.repositories.AccountRepository;

public class AccountServiceImpl implements AccountService{

	private AccountRepository accountService;
	
	@Autowired
	public AccountServiceImpl(AccountRepository accountService) {
	
		this.accountService = accountService;
	}



	@Override
	public List<AccountEntity> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

}
