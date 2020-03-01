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
import uk.kissgergely.managementservice.data.entities.JpaConstants;
import uk.kissgergely.managementservice.data.repositories.AccountRepository;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.services.exceptions.ServiceExceptionConstants;

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
        return new ArrayList<>(accountRepo.findAllByDeletedFalse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountEntity getAccount(Integer id) throws AccountNotFoundException {
        return accountRepo.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new AccountNotFoundException(ServiceExceptionConstants.ACCOUNT_NOT_FOUND_BY_ID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountEntity updateAccount(AccountEntity accountEntity)
            throws AccountNotFoundException, AccountAlreadyExistException {
        AccountEntity originalAccount = getAccount(accountEntity.getId());
        validateIfAlreadyExistWithTheSameName(accountEntity);
        if(!originalAccount.getName().equals(accountEntity.getName()))
            originalAccount.setName(accountEntity.getName());
        if(!originalAccount.getDescription().equals(accountEntity.getDescription()))
            originalAccount.setDescription(accountEntity.getDescription());
        if(!originalAccount.getTagEntitySet().equals(accountEntity.getTagEntitySet()))
            originalAccount.setTagEntitySet(accountEntity.getTagEntitySet());
        return accountRepo.save(originalAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountEntity saveAccount(AccountEntity accountEntity) throws AccountAlreadyExistException {
        validateIfAlreadyExistWithTheSameName(accountEntity);
        return accountRepo.save(new AccountEntity(accountEntity.getName(), accountEntity.getDescription()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountEntity deleteAccount(Integer id) throws AccountNotFoundException {
        return markedAsDeleted(getAccount(id));
    }


    private void validateIfAlreadyExistWithTheSameName(AccountEntity accountEntity) throws AccountAlreadyExistException {
        Optional<AccountEntity> accountEntityOptional = accountRepo.findByNameAndDeletedFalse(accountEntity.getName());
        if(accountEntityOptional.isPresent()){
            if(accountEntity.getId() == null){
                throw new AccountAlreadyExistException(ServiceExceptionConstants.DIFFERENT_ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
            }
            if(!accountEntity.getId().equals(accountEntityOptional.get().getId())){
                throw new AccountAlreadyExistException(ServiceExceptionConstants.DIFFERENT_ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
            }
        }
    }

    private AccountEntity markedAsDeleted(AccountEntity accountEntity) {
        accountEntity.setDeleted(true);
        accountEntity.setName(JpaConstants.DELETED_PREFIX + accountEntity.getName() + UUID.randomUUID().toString());
        return accountRepo.save(accountEntity);
    }
}
