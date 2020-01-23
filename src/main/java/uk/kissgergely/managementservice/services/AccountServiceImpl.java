package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountServiceException;
import uk.kissgergely.managementservice.exceptions.ServiceConstants;
import uk.kissgergely.managementservice.exceptions.ServiceExceptionConstants;
import uk.kissgergely.managementservice.repositories.AccountRepository;

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
		List<AccountEntity> accountEntityList = new ArrayList<AccountEntity>();
		accountRepo.findByDeletedFalse().forEach(accountEntityList::add);
		LOG.debug("getAllAccounts: accountEntityList {}", accountEntityList);
		return accountEntityList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity getAccount(String hostReference) throws AccountServiceException {
		LOG.info("getAccount: called with hostReference {}", hostReference);
		Optional<AccountEntity> accountEntityOptional = accountRepo.findByHostReferenceAndDeletedFalse(hostReference);
		if (accountEntityOptional.isPresent()) {
			LOG.info("getAccount: accountEntity found {}", accountEntityOptional.get());
			return accountEntityOptional.get();
		} else
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity updateAccount(AccountEntity accountEntity) throws AccountServiceException {
		LOG.info("updateAccount: called with {}", accountEntity);
		AccountEntity originalAccount = getAccount(accountEntity.getHostReference());
		LOG.info("updateAccount: original account {}", originalAccount);
		originalAccount.setAccountName(accountEntity.getAccountName());
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
	public AccountEntity saveAccount(AccountEntity accountEntity) throws AccountServiceException {
		LOG.info("saveAccount: called with accountEntity {}", accountEntity);
		if (accountRepo.findByAccountName(accountEntity.getAccountName()).isPresent()) {
			LOG.info("saveAccount: accountEntity {} is already exist with the same name.", accountEntity);
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_ALREADY_EXIST_WITH_THAT_NAME);
		}
		AccountEntity newAccount = new AccountEntity();
		newAccount.setAccountName(accountEntity.getAccountName());
		newAccount.setDescription(accountEntity.getDescription());
		newAccount.setHostReference(accountEntity.getHostReference());
		accountRepo.save(newAccount);
		LOG.info("saveAccount: account saved {}", newAccount);
		return newAccount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String deleteAccount(String hostReference) throws AccountServiceException {
		LOG.info("deleteAccount: called with hostReference {}", hostReference);
		AccountEntity accountToDelete = getAccount(hostReference);
		LOG.info("deleteAccount: accountToDelete {}", accountToDelete);
		if (accountToDelete.getDeleted()) {
			LOG.info("deleteAccount: account already deleted accountToDelete {}", accountToDelete);
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND);
		}
		accountToDelete.setDeleted(true);
		accountToDelete.setAccountName(
				"__________" + accountToDelete.getAccountName() + "__________" + UUID.randomUUID().toString());
		LOG.info("deleteAccount: deleted accountToDelete {}", accountToDelete);
		accountRepo.save(accountToDelete);
		return ServiceConstants.DELETING_ACCOUNT_WAS_SUCCESSFUL;
	}

}
