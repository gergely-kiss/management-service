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
	 * @param hostReference
	 * @return
	 * @throws AccountNotFoundException
	 */
	Optional<AccountEntity> getAccount(String hostReference)
			throws AccountNotFoundException;

	/**
	 * @param accountEntity
	 * @return
	 * @throws Exception
	 */
	Optional<AccountEntity> updateAccount(AccountEntity accountEntity)
			throws AccountNotFoundException, AccountAlreadyExistException ;

	/**
	 * @param accountEntity
	 * @return
	 * @throws AccountAlreadyExistException
	 */
	Optional<AccountEntity> saveAccount(AccountEntity accountEntity)
			throws AccountAlreadyExistException;

	/**
	 * @param hostReference
	 * @return
	 * @throws AccountNotFoundException
	 */
	Optional<AccountEntity> deleteAccount(String hostReference)
			throws AccountNotFoundException;

}
