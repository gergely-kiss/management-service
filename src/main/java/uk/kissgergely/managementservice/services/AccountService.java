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
	AccountEntity updateOrSaveAccount(AccountEntity accountEntity) throws AccountServiceException;

	/**
	 * @param accountEntity
	 * @return deleted account
	 * @throws AccountServiceException
	 */
	AccountEntity deleteAccount(String hostReference) throws AccountServiceException;

}
