package uk.kissgergely.managementservice.data.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.junit.jupiter.api.Test;

import uk.kissgergely.managementservice.services.TestContstants;
import uk.kissgergely.managementservice.unittesttools.AssertAnnotations;

class AccountEntityTest {

	@Test
	void typeAnnotations() {
		AssertAnnotations.assertType(AccountEntity.class, Entity.class, Table.class);
	}
	
	@Test
	public void fieldAnnotations() {
		AssertAnnotations.assertField(AccountEntity.class, TestContstants.ID, Id.class, GeneratedValue.class, Column.class);
		AssertAnnotations.assertField(AccountEntity.class, TestContstants.ACCOUNT_NAME, Column.class);
		AssertAnnotations.assertField(AccountEntity.class, TestContstants.DESCRIPTION, Column.class);
		AssertAnnotations.assertField(AccountEntity.class, TestContstants.HOST_REFERENCE, Column.class);
		AssertAnnotations.assertField(AccountEntity.class, TestContstants.DELETED, Column.class);
	}
	

}
