package uk.kissgergely.managementservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.data.repositories.AccountRepository;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.services.exceptions.ServiceExceptionConstants;
import uk.kissgergely.managementservice.services.resources.ServiceConstants;

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
        Optional<AccountEntity> accountEntityOptional = accountRepo.findByHostReferenceAndDeletedFalse(hostReference);
        if (accountEntityOptional.isPresent()) return accountEntityOptional;
        else throw new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> updateAccount(AccountEntity accountEntity)
            throws AccountNotFoundException, AccountAlreadyExistException {
        AccountEntity originalAccount = getAccount(accountEntity.getHostReference()).orElseThrow(null);
        Optional<AccountEntity> check = accountRepo.findByNameAndDeletedFalse(accountEntity.getName());
        if (check.isPresent()
                && !originalAccount.getHostReference().equals(check.get().getHostReference())) {
            throw new AccountAlreadyExistException(
                    ServiceExceptionConstants.DIFFERENT_ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
        }
        originalAccount.setName(accountEntity.getName());
        originalAccount.setDescription(accountEntity.getDescription());
        originalAccount.setHostReference(accountEntity.getHostReference());
        originalAccount.setDeleted(accountEntity.getDeleted());
        accountRepo.save(originalAccount);
        return Optional.of(originalAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> saveAccount(AccountEntity accountEntity) throws AccountAlreadyExistException {
        LOG.info("saveAccount: called with accountEntity {}", accountEntity);
        if (accountRepo.findByNameAndDeletedFalse(accountEntity.getName()).isPresent()) {
            throw new AccountAlreadyExistException(ServiceExceptionConstants.ACCOUNT_ALREADY_EXIST_WITH_THAT_NAME);
        }
        AccountEntity newAccount = new AccountEntity();
        newAccount.setName(accountEntity.getName());
        newAccount.setDescription(accountEntity.getDescription());
        newAccount.setHostReference(accountEntity.getHostReference());
        accountRepo.save(newAccount);
        return Optional.of(newAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteAccount(String hostReference) throws AccountNotFoundException {
        AccountEntity accountToDelete = getAccount(hostReference).orElseThrow(null);
        if (accountToDelete.getDeleted()) throw new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND);

        accountToDelete.setDeleted(true);
        accountToDelete.setName("__________" + accountToDelete.getName() + "__________" + UUID.randomUUID().toString());
        accountRepo.save(accountToDelete);
        return ServiceConstants.DELETING_ACCOUNT_WAS_SUCCESSFUL;
    }
}
