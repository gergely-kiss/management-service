package uk.kissgergely.managementservice.services;

import java.util.List;
import java.util.Optional;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;

public interface AccountService {

	/**
	 * @return
	 */
	List<AccountEntity> getAllAccounts();

	/**
	 * @param id
	 * @return
	 * @throws AccountNotFoundException
	 */
	AccountEntity getAccount(Integer id)
			throws AccountNotFoundException;

	/**
	 * @param accountEntity
	 * @return
	 * @throws Exception
	 */
	AccountEntity updateAccount(AccountEntity accountEntity)
			throws AccountNotFoundException, AccountAlreadyExistException ;

	/**
	 * @param accountEntity
	 * @return
	 * @throws AccountAlreadyExistException
	 */
	AccountEntity saveAccount(AccountEntity accountEntity)
			throws AccountAlreadyExistException;

	/**
	 * @param id
	 * @return
	 * @throws AccountNotFoundException
	 */
	AccountEntity deleteAccount(Integer id)
			throws AccountNotFoundException;

}
