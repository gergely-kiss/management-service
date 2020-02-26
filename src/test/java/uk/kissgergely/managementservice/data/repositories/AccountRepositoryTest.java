package uk.kissgergely.managementservice.data.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.unittesttools.TestContstants;

@SpringBootTest
@AutoConfigureTestDatabase
class AccountRepositoryTest {

    AccountRepository reporsitory;

    AccountEntity accountEntity;
    AccountEntity accountEntity2;
    AccountEntity accountEntityDeleted;
    AccountEntity accountEntitySaved;
    AccountEntity accountEntity2Saved;
    AccountEntity accountEntityDeletedSaved;

    @Autowired
    AccountRepositoryTest(AccountRepository repository) {
	this.reporsitory = repository;
    }

    @BeforeEach
    void setup() {
	accountEntity = new AccountEntity(TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1);
	accountEntitySaved = reporsitory.save(accountEntity);
	accountEntity2 = new AccountEntity(TestContstants.TEST_NAME_2, TestContstants.TEST_DESCRIPTION_2);

	accountEntity2Saved = reporsitory.save(accountEntity2);
	accountEntityDeleted = new AccountEntity(TestContstants.TEST_NAME_FOR_DELETE,
		TestContstants.TEST_DESCRIPTION_1);
	accountEntityDeleted.setDeleted(true);
	accountEntityDeletedSaved = reporsitory.save(accountEntityDeleted);
    }

    @Test
    void saved() {

	assertNotNull(accountEntitySaved);
	assertNotNull(accountEntitySaved.getId());
	assertFalse(accountEntitySaved.getDeleted());
	assertNotNull(accountEntitySaved.getHostReference());
	assertEquals(accountEntity.getName(), accountEntitySaved.getName());
	assertEquals(accountEntity.getDescription(), accountEntitySaved.getDescription());

    }

    @Test
    void findByHostReferenceAndDeletedFalse() {
	assertTrue(reporsitory.findByHostReferenceAndDeletedFalse(accountEntitySaved.getHostReference()).isPresent());
	assertFalse(reporsitory.findByHostReferenceAndDeletedFalse(accountEntityDeletedSaved.getHostReference())
		.isPresent());
    }

    @Test
    void findByDeletedFalse() {
	assertTrue(((List<?>) reporsitory.findAll()).size() > ((List<?>) reporsitory.findByDeletedFalse()).size());
    }

    @Test
    void findByName() {
	assertTrue((reporsitory.findByNameAndDeletedFalse(TestContstants.TEST_NAME_1).isPresent()
		&& reporsitory.findByNameAndDeletedFalse(TestContstants.TEST_NAME_1).get().getDescription()
			.equals(TestContstants.TEST_DESCRIPTION_1)
		&& reporsitory.findByNameAndDeletedFalse(TestContstants.TEST_NAME_1).get().getId() == accountEntitySaved.getId()));
    }
}
