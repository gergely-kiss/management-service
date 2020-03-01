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
        AssertAnnotations.assertField(AccountEntity.class, TestContstants.ID, Id.class, GeneratedValue.class,
                Column.class);
        AssertAnnotations.assertField(AccountEntity.class, TestContstants.NAME, Column.class);
        AssertAnnotations.assertField(AccountEntity.class, TestContstants.DESCRIPTION, Column.class);
        AssertAnnotations.assertField(AccountEntity.class, TestContstants.DELETED, Column.class);
        AssertAnnotations.assertField(AccountEntity.class, TestContstants.BALANCE, Column.class);
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
        assertEquals(false, accountEntity.getDeleted());
        assertEquals(0, accountEntity.getBalance());

        accountEntity = new AccountEntity(TestContstants.TEST_NAME, TestContstants.TEST_DESCRIPTION);
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(0, accountEntity.getBalance());
        assertEquals(false, accountEntity.getDeleted());

        accountEntity = new AccountEntity(TestContstants.TEST_ID, TestContstants.TEST_NAME, TestContstants.TEST_DESCRIPTION);
        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(0, accountEntity.getBalance());
        assertEquals(false, accountEntity.getDeleted());

        accountEntity = new AccountEntity(TestContstants.TEST_ID, TestContstants.TEST_NAME,
                TestContstants.TEST_DESCRIPTION, TestContstants.TEST_BALANCE);
        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(TestContstants.TEST_BALANCE, accountEntity.getBalance());
        assertEquals(false, accountEntity.getDeleted());

        accountEntity = new AccountEntity(TestContstants.TEST_ID, TestContstants.TEST_NAME,
                TestContstants.TEST_DESCRIPTION, TestContstants.TEST_BALANCE, TestContstants.TEST_TAG_SET);

        assertEquals(TestContstants.TEST_ID, accountEntity.getId());
        assertEquals(TestContstants.TEST_NAME, accountEntity.getName());
        assertEquals(TestContstants.TEST_DESCRIPTION, accountEntity.getDescription());
        assertEquals(TestContstants.TEST_BALANCE, accountEntity.getBalance());
        assertEquals(TestContstants.TEST_TAG_SET, accountEntity.getTagEntitySet());
        assertEquals(false, accountEntity.getDeleted());
    }

    @Test
    public void toStringTest() {
    }


}
