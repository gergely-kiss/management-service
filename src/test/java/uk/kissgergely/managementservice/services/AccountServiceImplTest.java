package uk.kissgergely.managementservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;


import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.data.repositories.AccountRepository;

class AccountServiceImplTest {
	
	AccountService accountService;
	
	@MockBean
	AccountRepository accountRepoMock;

	AccountEntity accountEntity1;
	AccountEntity accountEntity2;

	AccountEntity savedAccountEntity1;
	AccountEntity savedAccountEntity2;

	List<AccountEntity> savedAccountEntityList;
	
	@BeforeEach
	void setUp() {
		
		accountService = new AccountServiceImpl(accountRepoMock);
		
		accountEntity1 = new AccountEntity();
		accountEntity1.setAccountName(TestContstants.ACCOUNT_NAME_1);
		accountEntity1.setDescription(TestContstants.ACCOUNT_DESC_1);
		accountEntity1.setHostReference(TestContstants.ACCOUNT_HOSTREF_1);
		

		accountEntity2 = new AccountEntity();
		accountEntity2.setAccountName(TestContstants.ACCOUNT_NAME_2);
		accountEntity2.setDescription(TestContstants.ACCOUNT_DESC_2);
		accountEntity2.setHostReference(TestContstants.ACCOUNT_HOSTREF_2);
		
		savedAccountEntity1 = new AccountEntity();
		savedAccountEntity1.setId(TestContstants.ACCOUNT_ID_1);
		savedAccountEntity1.setAccountName(TestContstants.ACCOUNT_NAME_1);
		savedAccountEntity1.setDescription(TestContstants.ACCOUNT_DESC_1);
		savedAccountEntity1.setHostReference(TestContstants.ACCOUNT_HOSTREF_1);
		savedAccountEntity1.setDeleted(false);

		savedAccountEntity2 = new AccountEntity();
		savedAccountEntity2.setId(TestContstants.ACCOUNT_ID_2);
		savedAccountEntity2.setAccountName(TestContstants.ACCOUNT_NAME_2);
		savedAccountEntity2.setDescription(TestContstants.ACCOUNT_DESC_2);
		savedAccountEntity2.setHostReference(TestContstants.ACCOUNT_HOSTREF_2);
		savedAccountEntity2.setDeleted(false);
		
		savedAccountEntityList = new ArrayList<AccountEntity>();
		savedAccountEntityList.add(savedAccountEntity1);
		savedAccountEntityList.add(savedAccountEntity2);
		
		/*
		 * when(accountRepoMock.save(accountEntity1)).thenReturn(savedAccountEntity1);
		 * when(accountRepoMock.save(accountEntity2)).thenReturn(savedAccountEntity2);
		 * when(accountRepoMock.findByDeletedFalse()).thenReturn(savedAccountEntityList)
		 * ;
		 */
	}

	/*
	 * @Test void testGetAllAccounts() {
	 * 
	 * List<AccountEntity> getAllAccountEntityList =
	 * accountService.getAllAccounts();
	 * 
	 * assertEquals(savedAccountEntityList, getAllAccountEntityList);
	 * 
	 * }
	 */

	/*
	 * @Test void testGetAccount() { }
	 * 
	 * @Test void testUpdateAccount() {
	 * 
	 * }
	 * 
	 * @Test void testSaveAccount() {
	 * 
	 * }
	 * 
	 * @Test void testDeleteAccount() {
	 * 
	 * }
	 */
}
