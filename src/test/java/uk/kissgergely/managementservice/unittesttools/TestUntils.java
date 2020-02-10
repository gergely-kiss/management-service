package uk.kissgergely.managementservice.unittesttools;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUntils {
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
