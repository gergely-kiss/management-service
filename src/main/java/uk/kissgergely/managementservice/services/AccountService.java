package uk.kissgergely.managementservice.services;

import java.util.List;
import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountException;

public interface AccountService {

	/**
	 * @return All accounts
	 */
	List<AccountEntity> getAllAccounts();

	/**
	 * @param id
	 * @return Account entity
	 * @throws AccountException
	 */
	AccountEntity getAccount(String id) throws AccountException;

	/**
	 * @param accountEntity
	 * @return Updated or saved account entity
	 * @throws AccountException
	 */
	AccountEntity updateOrSaveAccount(AccountEntity accountEntity) throws AccountException;

	/**
	 * @param accountEntity
	 * @return deleted account
	 * @throws AccountException
	 */
	AccountEntity deleteAccount(AccountEntity accountEntity) throws AccountException;

}
