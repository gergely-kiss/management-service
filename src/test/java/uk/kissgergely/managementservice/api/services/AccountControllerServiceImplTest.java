package uk.kissgergely.managementservice.api.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import uk.kissgergely.managementservice.api.exceptions.AccountNotFoundControllerException;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.dtos.AccountDTO;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.unittesttools.TestContstants;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;
class AccountControllerServiceImplTest {
	
	AccountControllerService accountControllerService;
	
	@Mock
	AccountService accountService;
	
	@Mock
	AccountDTO accountDTO;
	
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
		
		
	}
	
	@Test
	void getAllAccounts() throws AccountNotFoundException {
	
		accountEntity.setId(TestContstants.TEST_ID_1);
		List<AccountEntity> accountEntityList = new ArrayList<>();
		accountEntityList.add(accountEntity);
		when(accountService.getAllAccounts()).thenReturn(accountEntityList);
		assertEquals(accountResponsesList.toString(), accountControllerService.getAllAccounts().toString());
	}
	

	@Test
	void getAccountById() throws AccountNotFoundException {
		when(accountService.getAccount(TestContstants.TEST_HOST_REFERENCE_1)).thenReturn(accountEntity);
		assertEquals(accountResponse.toString(), accountControllerService.getAccountById(TestContstants.TEST_HOST_REFERENCE_1).toString());
	}

	@Test
	void getAccountByIdThrow() throws AccountNotFoundException {
		when(accountService.getAccount(TestContstants.TEST_HOST_REFERENCE_1)).thenThrow(AccountNotFoundException.class);
		AccountNotFoundControllerException exception = assertThrows(AccountNotFoundControllerException.class, ()->{
			accountControllerService.getAccountById(TestContstants.TEST_HOST_REFERENCE_1);
		});
		assertEquals(new AccountNotFoundControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.ACCOUNT_NOT_FOUND).getMessage(), exception.getMessage());
		
	}
}
