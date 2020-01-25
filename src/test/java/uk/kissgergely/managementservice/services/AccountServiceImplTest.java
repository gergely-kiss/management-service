package uk.kissgergely.managementservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import uk.kissgergely.managementservice.data.repositories.AccountRepository;

class AccountServiceImplTest {
	
	AccountService accountService;
	
	@Mock
	AccountRepository accountRepoMock;
	
	@BeforeEach
	void setUp() {
		accountService = new AccountServiceImpl(accountRepoMock);
	}

	@Test
	void testGetAllAccounts() {
		
	}

	@Test
	void testGetAccount() {
	}

	@Test
	void testUpdateAccount() {
		
	}

	@Test
	void testSaveAccount() {
		
	}

	@Test
	void testDeleteAccount() {
		
	}

}
