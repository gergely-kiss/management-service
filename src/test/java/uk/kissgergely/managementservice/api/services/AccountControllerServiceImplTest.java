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

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.unittesttools.TestContstants;
import uk.kissgergely.managementservice.vos.AccountVO;

class AccountControllerServiceImplTest {
	
	AccountControllerService accountControllerService;
	
	@Mock
	AccountService accountService;
	
	AccountVO accountVO;
	AccountEntity accountEntity;
	
	@BeforeEach
	void setUp() throws AccountNotFoundException {
		MockitoAnnotations.initMocks(this);
		
		
		
		accountControllerService = new AccountControllerServiceImpl(accountService);
		when(accountService.getAccount(TestContstants.TEST_HOST_REFERENCE_1)).thenReturn(accountEntity);
		
	}

}
