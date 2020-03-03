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
    private AccountEntity accountEntitySaved;
    @Autowired
    AccountRepositoryTest(AccountRepository repository) {
        this.accountRepository = repository;
    }

    @BeforeEach
    void setup() {

    }

    @Test
    void findByIdAndDeletedFalse() {
        accountEntity = new AccountEntity(TestContstants.TEST_NAME, TestContstants.TEST_DESCRIPTION);

        accountEntity.setDeleted(true);
        accountEntitySaved = accountRepository.save(accountEntity);
        assertFalse(accountRepository.findByIdAndDeletedFalse(accountEntitySaved.getId()).isPresent());

        accountEntity.setDeleted(false);
        accountEntitySaved = accountRepository.save(accountEntity);
        assertTrue(accountRepository.findByIdAndDeletedFalse(accountEntitySaved.getId()).isPresent());
    }

    @Test
    void findAllByDeletedFalse() {

    }

    @Test
    void findByNameAndDeletedFalse() {

    }
}
