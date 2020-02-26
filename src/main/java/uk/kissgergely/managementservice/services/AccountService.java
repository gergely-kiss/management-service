package uk.kissgergely.managementservice.services;

import java.util.List;
import java.util.Optional;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;

public interface AccountService {

	List<AccountEntity> getAllAccounts();

	Optional<AccountEntity> getAccount(String hostReference) throws AccountNotFoundException;
	Optional<AccountEntity> updateAccount(AccountEntity accountEntity) throws AccountNotFoundException, AccountAlreadyExistException;
	Optional<AccountEntity> saveAccount(AccountEntity accountEntity) throws AccountAlreadyExistException;
	String deleteAccount(String hostReference) throws AccountNotFoundException;

}
