package uk.kissgergely.managementservice.api.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.unittesttools.TestContstants;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;
class AccountControllerServiceImplTest {
	
	AccountControllerService accountControllerService;
	
	@Mock
	AccountService accountService;
	
	AccountEntity accountEntity;
	AccountRequest accountRequest;
	AccountResponse accountResponse;
	List<AccountResponse> accountResponsesList; 	
	@BeforeEach
	void setUp() throws AccountNotFoundException {
		MockitoAnnotations.initMocks(this);
		
		accountEntity = new AccountEntity(TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1, TestContstants.TEST_HOST_REFERENCE_1);
		accountResponsesList = new ArrayList<>();
		accountResponse = new AccountResponse(TestContstants.TEST_HOST_REFERENCE_1, TestContstants.TEST_NAME_1, TestContstants.TEST_DESCRIPTION_1);
		accountResponsesList.add(accountResponse);
		
		
		accountControllerService = new AccountControllerServiceImpl(accountService);
		when(accountService.getAccount(TestContstants.TEST_HOST_REFERENCE_1)).thenReturn(accountEntity);
		
	}
	
	@Test
	void getAllAccounts() {
		accountEntity.setId(TestContstants.TEST_ID_1);
		List<AccountEntity> accountEntityList = new ArrayList<>();
		accountEntityList.add(accountEntity);
		when(accountService.getAllAccounts()).thenReturn(accountEntityList);
		
		assertEquals(accountResponsesList.toString(), accountControllerService.getAllAccounts().toString());
	}
	
	
	//TODO FIX
	//@Test
	void getAllAccountsThrow() {
		accountEntity.setId(TestContstants.TEST_ID_1);
		List<AccountEntity> accountEntityList = new ArrayList<>();
		accountEntityList.add(accountEntity);
		when(accountService.getAllAccounts()).thenThrow(AccountNotFoundException.class);
		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
			accountControllerService.getAllAccounts();
		});
		
		assertEquals(exception.getMessage(), ControllerResponseConstants.ACCOUNT_NOT_FOUND);
	}

}
