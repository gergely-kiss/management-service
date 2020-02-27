package uk.kissgergely.managementservice.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.unittesttools.TestContstants;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class AccountRepositoryTest {

    private AccountRepository accountRepository;
    private AccountEntity accountEntity;
    private AccountEntity accountEntityDeleted;
    private AccountEntity accountEntitySaved;
    private AccountEntity accountEntityDeletedSaved;

    @Autowired
    AccountRepositoryTest(AccountRepository repository) {
        this.accountRepository = repository;
    }

    @BeforeEach
    void setup() {

    }

    @Test
    void findByHostReferenceAndDeletedFalse() {
        accountEntityDeleted = new AccountEntity(TestContstants.TEST_NAME_FOR_DELETE,
                TestContstants.TEST_DESCRIPTION_1);
        accountEntityDeleted.setDeleted(true);
        accountEntityDeletedSaved = accountRepository.save(accountEntityDeleted);
        accountEntity = new AccountEntity(TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1);
        accountRepository.save(accountEntity);
        assertTrue(accountRepository.findByHostReferenceAndDeletedFalse(accountEntitySaved.getHostReference()).isPresent());
        assertFalse(accountRepository.findByHostReferenceAndDeletedFalse(accountEntityDeletedSaved.getHostReference())
                .isPresent());
    }

    @Test
    void findAllByDeletedFalse() {
        accountEntityDeleted = new AccountEntity(TestContstants.TEST_NAME_FOR_DELETE,
                TestContstants.TEST_DESCRIPTION_1);
        accountEntityDeleted.setDeleted(true);
        accountEntityDeletedSaved = accountRepository.save(accountEntityDeleted);
        assertTrue(((List<?>) accountRepository.findAll()).size() >
                ((List<?>) accountRepository.findAllByDeletedFalse()).size());
    }

    @Test
    void findByNameAndDeletedFalse() {
        accountEntity = new AccountEntity(TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1);
        accountRepository.save(accountEntity);
        accountEntityDeleted = new AccountEntity(TestContstants.TEST_NAME_FOR_DELETE,
                TestContstants.TEST_DESCRIPTION_1);
        accountEntityDeleted.setDeleted(true);

        assertTrue(accountRepository.findByNameAndDeletedFalse(TestContstants.TEST_NAME_1).isPresent());
        assertFalse(accountRepository.findByNameAndDeletedFalse(TestContstants.TEST_NAME_FOR_DELETE).isPresent());
    }
}
