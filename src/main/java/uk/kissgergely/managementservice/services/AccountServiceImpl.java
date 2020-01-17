package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountException;
import uk.kissgergely.managementservice.exceptions.ExceptionConstants;
import uk.kissgergely.managementservice.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepo;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AccountEntity> getAllAccounts() {
		List<AccountEntity> accountEntityList = new ArrayList<AccountEntity>();
		accountRepo.findByDeletedFalse().forEach(accountEntityList::add);
		return accountEntityList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity getAccount(String id) throws AccountException {
		if (accountRepo.findByHostReference(id).isPresent())
			return accountRepo.findByHostReference(id).get();
		else
			throw new AccountException(ExceptionConstants.ACCOUNT_EXCEPTION);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity updateOrSaveAccount(AccountEntity accountEntity) {
		try {
			AccountEntity account = getAccount(accountEntity.getHostReference());
			accountEntity.setAccountName(account.getAccountName());
			accountEntity.setDescription(account.getDescription());
			accountEntity.setHostReference(UUID.randomUUID().toString());
			return accountRepo.save(account);
		} catch (AccountException e) {
			return accountRepo.save(accountEntity);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity deleteAccount(AccountEntity accountEntity) throws AccountException {
		AccountEntity account = getAccount(accountEntity.getHostReference());
		if (account.getDeleted())
			throw new AccountException(ExceptionConstants.ACCOUNT_EXCEPTION);
		account.setDeleted(true);
		return accountRepo.save(account);
	}

}
