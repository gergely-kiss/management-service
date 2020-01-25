package uk.kissgergely.managementservice.services;

import java.util.List;
import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.exceptions.AccountNotFoundException;

public interface AccountService {

	/**
	 * @return All accounts
	 */
	List<AccountEntity> getAllAccounts();

	/**
	 * @param id
	 * @return Account entity
	 * @throws AccountNotFoundException
	 */
	AccountEntity getAccount(String hostReference) throws AccountNotFoundException;

	/**
	 * @param accountEntity
	 * @return Updated or saved account entity
	 * @throws AccountNotFoundException
	 * @throws AccountAlreadyExistException
	 */
	AccountEntity updateAccount(AccountEntity accountEntity) throws AccountNotFoundException, AccountAlreadyExistException;

	AccountEntity saveAccount(AccountEntity accountEntity) throws AccountAlreadyExistException;
	
	/**
	 * @param accountEntity
	 * @return deleted account
	 * @throws AccountNotFoundException
	 */
	String deleteAccount(String hostReference) throws AccountNotFoundException;

}
