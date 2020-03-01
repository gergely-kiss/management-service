package uk.kissgergely.managementservice.unittesttools;

import uk.kissgergely.managementservice.data.entities.TagEntity;

import java.util.HashSet;
import java.util.Set;

public class TestContstants {


	public static final Integer TEST_ID = 1;
	public static final String TEST_NAME = "test name";
	public static final String TEST_DESCRIPTION = "test description";
	public static final Double TEST_BALANCE = 10D;
	public static final Set<TagEntity> TEST_TAG_SET = new HashSet();
	public static final Boolean TEST_DELETED = true;
	private TestContstants() {
	}
}
