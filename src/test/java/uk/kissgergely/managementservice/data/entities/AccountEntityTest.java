package uk.kissgergely.managementservice.data.entities;

import org.junit.jupiter.api.Test;
import uk.kissgergely.managementservice.unittesttools.AssertAnnotations;
import uk.kissgergely.managementservice.unittesttools.ReflectionTool;
import uk.kissgergely.managementservice.unittesttools.TestContstants;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountEntityTest {

    @Test
    void typeAnnotations() {
        AssertAnnotations.assertType(AccountEntity.class, Entity.class, Table.class);
    }

    @Test
    public void fieldAnnotations() {
        AssertAnnotations.assertField(AccountEntity.class, JpaConstants.ID, Id.class, GeneratedValue.class,
                Column.class);
        AssertAnnotations.assertField(AccountEntity.class, JpaConstants.NAME, Column.class);
        AssertAnnotations.assertField(AccountEntity.class, JpaConstants.DESCRIPTION, Column.class);
        AssertAnnotations.assertField(AccountEntity.class, JpaConstants.DELETED, Column.class);
        AssertAnnotations.assertField(AccountEntity.class, JpaConstants.BALANCE, Column.class);
    }

    @Test
    public void entity() {
        Entity accountEntity = ReflectionTool.getClassAnnotation(AccountEntity.class, Entity.class);
        assertEquals("", accountEntity.name());
    }

    @Test
    public void table() {
        Table table = ReflectionTool.getClassAnnotation(AccountEntity.class, Table.class);
        assertEquals(JpaConstants.ACCOUNT, table.name());
    }

    @Test
    public void constructor() {
        AccountEntity accountEntity = new AccountEntity();
        assertEquals(JpaConstants.DELETED_DEFAULT, accountEntity.getDeleted());
        assertEquals(JpaConstants.BALANCE_DEFAULT, accountEntity.getBalance());

        accountEntity = new AccountEntity(TestContstants.TEST_NAME, TestContstants.TEST_DESCRIPTION);
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(JpaConstants.BALANCE_DEFAULT, accountEntity.getBalance());
        assertEquals(JpaConstants.DELETED_DEFAULT, accountEntity.getDeleted());

        accountEntity = new AccountEntity(TestContstants.TEST_ID, TestContstants.TEST_NAME, TestContstants.TEST_DESCRIPTION);
        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(JpaConstants.BALANCE_DEFAULT, accountEntity.getBalance());
        assertEquals(JpaConstants.DELETED_DEFAULT, accountEntity.getDeleted());

        accountEntity = new AccountEntity(TestContstants.TEST_ID, TestContstants.TEST_NAME,
                TestContstants.TEST_DESCRIPTION, TestContstants.TEST_BALANCE);
        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(TestContstants.TEST_BALANCE, accountEntity.getBalance());
        assertEquals(JpaConstants.DELETED_DEFAULT, accountEntity.getDeleted());

        accountEntity = new AccountEntity(TestContstants.TEST_ID, TestContstants.TEST_NAME,
                TestContstants.TEST_DESCRIPTION, TestContstants.TEST_BALANCE, TestContstants.TEST_TAG_SET);

        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(TestContstants.TEST_BALANCE, accountEntity.getBalance());
        assertEquals(TestContstants.TEST_TAG_SET, accountEntity.getTagEntitySet());
        assertEquals(JpaConstants.DELETED_DEFAULT, accountEntity.getDeleted());
    }

    @Test
    public void settersAndGetters(){
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(TestContstants.TEST_ID);
        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        accountEntity.setName(TestContstants.TEST_NAME);
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        accountEntity.setDescription(TestContstants.TEST_DESCRIPTION);
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        accountEntity.setBalance(TestContstants.TEST_BALANCE);
        assertEquals(TestContstants.TEST_BALANCE, accountEntity.getBalance());
        accountEntity.setDeleted(TestContstants.TEST_DELETED);
        assertEquals(TestContstants.TEST_DELETED, accountEntity.getDeleted());
        accountEntity.setTagEntitySet(TestContstants.TEST_TAG_SET);
        assertEquals(TestContstants.TEST_TAG_SET, accountEntity.getTagEntitySet());
    }

    @Test
    public void toStringTest() {
    }


}
