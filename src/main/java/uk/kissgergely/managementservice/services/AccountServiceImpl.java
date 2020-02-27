package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.data.repositories.AccountRepository;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.services.exceptions.ServiceExceptionConstants;
import uk.kissgergely.managementservice.services.resources.ServiceConstants;

import javax.sql.rowset.serial.SerialException;

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
        return new ArrayList<>(accountRepo.findByDeletedFalse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> getAccount(String hostReference) throws AccountNotFoundException {
        return Optional.of(accountRepo.findByHostReferenceAndDeletedFalse(hostReference).orElseThrow(
                () -> new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID)));


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> updateAccount(AccountEntity accountEntity)
            throws AccountNotFoundException, AccountAlreadyExistException {
        AccountEntity originalAccount = getAccount(accountEntity.getHostReference()).orElseThrow(
                () -> new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID));
        validateIfAlreadyExistWithTheSameName(accountEntity.getName());

        originalAccount.setName(accountEntity.getName());
        originalAccount.setDescription(accountEntity.getDescription());

        return Optional.of(accountRepo.save(originalAccount));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> saveAccount(AccountEntity accountEntity) throws AccountAlreadyExistException {
        LOG.info("saveAccount: called with accountEntity {}", accountEntity);
        validateIfAlreadyExistWithTheSameName(accountEntity.getName());
        AccountEntity newAccount = new AccountEntity();
        newAccount.setName(accountEntity.getName());
        newAccount.setDescription(accountEntity.getDescription());
        newAccount.setHostReference(accountEntity.getHostReference());

        return Optional.of(accountRepo.save(newAccount));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> deleteAccount(String hostReference) throws AccountNotFoundException {
        AccountEntity accountToDelete = validateIfAccountAlreadyExist(hostReference);
        return Optional.of(markedAsDeleted(accountToDelete));
    }

    private AccountEntity validateIfAccountAlreadyExist(String hostReference) throws AccountNotFoundException {
        return getAccount(hostReference).orElseThrow(
                () -> new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID));
    }

    private AccountEntity validateIfAlreadyExistWithTheSameName(String accountName) throws AccountAlreadyExistException {
        return accountRepo.findByNameAndDeletedFalse(accountName).orElseThrow(
                () -> new AccountAlreadyExistException(ServiceExceptionConstants.DIFFERENT_ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME));
    }

    private AccountEntity markedAsDeleted(AccountEntity accountEntity) {
        accountEntity.setDeleted(true);
        accountEntity.setName("__________" + accountEntity.getName() + "__________" + UUID.randomUUID().toString());
        return accountRepo.save(accountEntity);
    }
}
