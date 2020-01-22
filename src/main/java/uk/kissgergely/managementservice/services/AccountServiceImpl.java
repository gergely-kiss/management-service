package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountServiceException;
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
		List<AccountEntity> accountEntityList = new ArrayList<AccountEntity>();
		accountRepo.findByDeletedFalse().forEach(accountEntityList::add);
		return accountEntityList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity getAccount(String hostReference) throws AccountServiceException {
		LOG.info("getAccount called with: hostReference {}", hostReference);
		if (accountRepo.findByHostReferenceAndDeletedFalse(hostReference).isPresent())
			return accountRepo.findByHostReferenceAndDeletedFalse(hostReference).get();
		else
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_EXCEPTION);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity updateAccount(AccountEntity accountEntity) throws AccountServiceException {
		LOG.info("updateAccount called with: {}", accountEntity);
		AccountEntity account = getAccount(accountEntity.getHostReference());
		LOG.info("updateAccount original account: {}", account);
		account.setAccountName(accountEntity.getAccountName());
		account.setDescription(accountEntity.getDescription());
		account.setHostReference(accountEntity.getHostReference());
		account.setDeleted(accountEntity.getDeleted());
		accountRepo.save(account);
		LOG.info("updateAccount account updated: {}", account);
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccountEntity saveAccount(AccountEntity accountEntity) throws AccountServiceException {
		LOG.info("saveAccount called with: {}", accountEntity);
		if(accountRepo.findByAccountName(accountEntity.getAccountName()).isPresent()) {
			LOG.info("saveAccount {} is already exist with the same name.", accountEntity);
			throw new AccountServiceException(ServiceExceptionConstants.ACCOUNT_EXCEPTION);} 
		AccountEntity account = new AccountEntity();
		account.setAccountName(accountEntity.getAccountName());
		account.setDescription(accountEntity.getDescription());
		account.setHostReference(accountEntity.getHostReference());
		accountRepo.save(account);
		LOG.info("saveAccount account saved : {}", account);
		return account;
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
		account.setAccountName(account.getAccountName()+ "_____" + UUID.randomUUID().toString());
		return accountRepo.save(account);
	}
	
}
