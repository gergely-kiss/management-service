package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.data.repositories.AccountRepository;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.services.exceptions.ServiceExceptionConstants;
import uk.kissgergely.managementservice.services.resources.ServiceConstants;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepo;

	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AccountEntity> getAllAccounts() {
		LOG.debug("getAllAccounts: called.");
		List<AccountEntity> accountEntityList = new ArrayList<>();
		accountRepo.findByDeletedFalse().forEach(accountEntityList::add);
		LOG.debug("getAllAccounts: accountEntityList {}", accountEntityList);
		return accountEntityList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity getAccount(String hostReference) throws AccountNotFoundException {
		LOG.info("getAccount: called with hostReference {}", hostReference);
		Optional<AccountEntity> accountEntityOptional = accountRepo.findByHostReferenceAndDeletedFalse(hostReference);
		if (accountEntityOptional.isPresent()) {
			LOG.info("getAccount: accountEntity found {}", accountEntityOptional.get());
			return accountEntityOptional.get();
		} else
			throw new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity updateAccount(AccountEntity accountEntity)
			throws AccountNotFoundException, AccountAlreadyExistException {
		LOG.info("updateAccount: called with {}", accountEntity);
		AccountEntity originalAccount = getAccount(accountEntity.getHostReference());
		if (accountRepo.findByName(accountEntity.getName()).isPresent()
				&& !originalAccount.getName().equals(accountEntity.getHostReference())) {
			throw new AccountAlreadyExistException(
					ServiceExceptionConstants.DIFFERENT_ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
		}
		originalAccount.setName(accountEntity.getName());
		originalAccount.setDescription(accountEntity.getDescription());
		originalAccount.setHostReference(accountEntity.getHostReference());
		originalAccount.setDeleted(accountEntity.getDeleted());
		accountRepo.save(originalAccount);
		LOG.info("updateAccount: account updated: {}", originalAccount);
		return originalAccount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity saveAccount(AccountEntity accountEntity) throws AccountAlreadyExistException {
		LOG.info("saveAccount: called with accountEntity {}", accountEntity);
		if (accountRepo.findByName(accountEntity.getName()).isPresent()) {
			LOG.info("saveAccount: check if exist by name", accountEntity.getName());
			throw new AccountAlreadyExistException(ServiceExceptionConstants.ACCOUNT_ALREADY_EXIST_WITH_THAT_NAME);
		}
		AccountEntity newAccount = new AccountEntity();
		newAccount.setName(accountEntity.getName());
		newAccount.setDescription(accountEntity.getDescription());
		newAccount.setHostReference(accountEntity.getHostReference());
		LOG.info("saveAccount: newAccount {}", newAccount);
		accountRepo.save(newAccount);
		LOG.info("saveAccount: account saved {}", newAccount);
		return newAccount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deleteAccount(String hostReference) throws AccountNotFoundException {
		LOG.info("deleteAccount: called with hostReference {}", hostReference);
		AccountEntity accountToDelete = getAccount(hostReference);
		LOG.info("deleteAccount: accountToDelete {}", accountToDelete);
		if (accountToDelete.getDeleted().booleanValue()) {
			LOG.info("deleteAccount: account already deleted accountToDelete {}", accountToDelete);
			throw new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND);
		}
		accountToDelete.setDeleted(true);
		accountToDelete.setName("__________" + accountToDelete.getName() + "__________" + UUID.randomUUID().toString());
		LOG.info("deleteAccount: deleted accountToDelete {}", accountToDelete);
		accountRepo.save(accountToDelete);
		return ServiceConstants.DELETING_ACCOUNT_WAS_SUCCESSFUL;
	}
}
