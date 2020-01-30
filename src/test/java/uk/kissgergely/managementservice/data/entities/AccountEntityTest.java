package uk.kissgergely.managementservice.data.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.junit.jupiter.api.Test;
import uk.kissgergely.managementservice.unittesttools.AssertAnnotations;
import uk.kissgergely.managementservice.unittesttools.ReflectionTool;
import uk.kissgergely.managementservice.unittesttools.TestContstants;

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
	AssertAnnotations.assertField(AccountEntity.class, TestContstants.HOST_REFERENCE, Column.class);
	AssertAnnotations.assertField(AccountEntity.class, TestContstants.TEST_DELETED, Column.class);
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
    public void contructorGetSet() {
	AccountEntity accountEntity = new AccountEntity();
	assertEquals(accountEntity.getDeleted(), false);

	accountEntity = new AccountEntity(TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1);
	assertEquals(TestContstants.TEST_NAME_1, accountEntity.getName());
	assertEquals(TestContstants.TEST_DESCRIPTION_1, accountEntity.getDescription());
	assertEquals(accountEntity.getDeleted(), false);

	accountEntity = new AccountEntity(TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1,
		TestContstants.TEST_HOST_REFERENCE_1);
	assertEquals(TestContstants.TEST_NAME_1, accountEntity.getName());
	assertEquals(TestContstants.TEST_DESCRIPTION_1, accountEntity.getDescription());
	assertEquals(TestContstants.TEST_HOST_REFERENCE_1, accountEntity.getHostReference());
	assertEquals(false, accountEntity.getDeleted());

	accountEntity = new AccountEntity(TestContstants.TEST_ID_1, TestContstants.TEST_NAME_1,
		TestContstants.TEST_DESCRIPTION_1, TestContstants.TEST_HOST_REFERENCE_1);
	assertEquals(TestContstants.TEST_ID_1, accountEntity.getId());
	assertEquals(TestContstants.TEST_NAME_1, accountEntity.getName());
	assertEquals(TestContstants.TEST_DESCRIPTION_1, accountEntity.getDescription());
	assertEquals(TestContstants.TEST_HOST_REFERENCE_1, accountEntity.getHostReference());
	assertEquals(false, accountEntity.getDeleted());
	
	accountEntity.setId(TestContstants.TEST_ID_2);
	accountEntity.setName(TestContstants.TEST_NAME_2);
	accountEntity.setDescription(TestContstants.TEST_DESCRIPTION_2);
	accountEntity.setHostReference(TestContstants.TEST_HOST_REFERENCE_2);
	accountEntity.setDeleted(true);
	
	assertEquals(TestContstants.TEST_ID_2,accountEntity.getId() );
	assertEquals(TestContstants.TEST_NAME_2, accountEntity.getName());
	assertEquals(TestContstants.TEST_DESCRIPTION_2, accountEntity.getDescription());
	assertEquals(TestContstants.TEST_HOST_REFERENCE_2, accountEntity.getHostReference());
	assertEquals(true, accountEntity.getDeleted());
	
    }
    @Test
    public void toStringTest() {
	AccountEntity accountEntity = new AccountEntity(TestContstants.TEST_ID_1, TestContstants.TEST_NAME_1,
		TestContstants.TEST_DESCRIPTION_1, TestContstants.TEST_HOST_REFERENCE_1);
	
	StringBuilder builder = new StringBuilder();
	builder.append("AccountEntity ["+TestContstants.ID+"=");
	builder.append(TestContstants.TEST_ID_1);
	builder.append(", "+TestContstants.NAME+"=");
	builder.append(TestContstants.TEST_NAME_1);
	builder.append(", "+TestContstants.DESCRIPTION+"=");
	builder.append(TestContstants.TEST_DESCRIPTION_1);
	builder.append(", "+TestContstants.HOST_REFERENCE+"=");
	builder.append(TestContstants.TEST_HOST_REFERENCE_1);
	builder.append(", " +TestContstants.TEST_DELETED+"=");
	builder.append(false);
	builder.append("]");
	
	String toString = new String(builder);
	
	assertEquals(toString, accountEntity.toString());
    }
}
