package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountServiceException;
import uk.kissgergely.managementservice.exceptions.ServiceExceptionConstants;
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
	public AccountEntity getAccount(String hostReference) throws AccountServiceException {
		if (accountRepo.findByHostReference(hostReference).isPresent())
			return accountRepo.findByHostReference(hostReference).get();
		else
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_EXCEPTION);
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
		} catch (AccountServiceException e) {
			AccountEntity account = new AccountEntity();
			accountEntity.setAccountName(account.getAccountName());
			accountEntity.setDescription(account.getDescription());
			accountEntity.setHostReference(UUID.randomUUID().toString());
			return accountRepo.save(account);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity deleteAccount(String hostReference) throws AccountServiceException {
		AccountEntity account = getAccount(hostReference);
		if (account.getDeleted())
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_EXCEPTION);
		account.setDeleted(true);
		return accountRepo.save(account);
	}

}
