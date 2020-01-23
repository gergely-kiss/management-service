package uk.kissgergely.managementservice.services;

import java.util.List;
import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountServiceException;

public interface AccountService {

	/**
	 * @return All accounts
	 */
	List<AccountEntity> getAllAccounts();

	/**
	 * @param id
	 * @return Account entity
	 * @throws AccountServiceException
	 */
	AccountEntity getAccount(String hostReference) throws AccountServiceException;

	/**
	 * @param accountEntity
	 * @return Updated or saved account entity
	 * @throws AccountServiceException
	 */
	AccountEntity updateAccount(AccountEntity accountEntity) throws AccountServiceException;

	AccountEntity saveAccount(AccountEntity accountEntity) throws AccountServiceException;
	
	/**
	 * @param accountEntity
	 * @return deleted account
	 * @throws AccountServiceException
	 */
	String deleteAccount(String hostReference) throws AccountServiceException;

}
