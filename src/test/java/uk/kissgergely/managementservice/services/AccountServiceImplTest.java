package uk.kissgergely.managementservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.data.repositories.AccountRepository;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.services.exceptions.ServiceExceptionConstants;
import uk.kissgergely.managementservice.services.resources.ServiceConstants;
import uk.kissgergely.managementservice.unittesttools.TestContstants;

class AccountServiceImplTest {

	AccountService accountService;

	@Mock
	AccountRepository accountRepoMock;

	AccountEntity accountEntity1;
	AccountEntity accountEntity2;
	AccountEntity accountEntityToSave;

	AccountEntity savedAccountEntity1;
	AccountEntity savedAccountEntityDelted;

	List<AccountEntity> savedAccountEntityList;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepoMock);

		accountEntity1 = new AccountEntity();
		accountEntity1.setName(TestContstants.TEST_NAME_1);
		accountEntity1.setDescription(TestContstants.TEST_DESCRIPTION_1);
		accountEntity1.setHostReference(TestContstants.TEST_HOST_REFERENCE_1);

		savedAccountEntity1 = new AccountEntity();
		savedAccountEntity1.setId(TestContstants.TEST_ID_1);
		savedAccountEntity1.setName(TestContstants.TEST_NAME_1);
		savedAccountEntity1.setDescription(TestContstants.TEST_DESCRIPTION_1);
		savedAccountEntity1.setHostReference(TestContstants.TEST_HOST_REFERENCE_1);
		savedAccountEntity1.setDeleted(false);


	}

	@Test
	void testGetAllAccounts() {
		savedAccountEntityList = new ArrayList<AccountEntity>();
		savedAccountEntityList.add(savedAccountEntity1);
		when(accountRepoMock.findByDeletedFalse()).thenReturn(savedAccountEntityList);
		assertEquals(savedAccountEntityList, accountService.getAllAccounts());
	}

	@Test
	void testGetAllEmptyList() {
		when(accountRepoMock.findByDeletedFalse()).thenReturn(new ArrayList<AccountEntity>());
		assertEquals(new ArrayList<AccountEntity>(), accountService.getAllAccounts());
	}

	@Test
	void testGetAccount() throws AccountNotFoundException {
		when(accountRepoMock.findByHostReferenceAndDeletedFalse(TestContstants.TEST_HOST_REFERENCE_1))
		.thenReturn(Optional.of(savedAccountEntity1));
		assertEquals(accountService.getAccount(TestContstants.TEST_HOST_REFERENCE_1), savedAccountEntity1);
	}

	@Test
	void testGetAccountThrowException() {
		when(accountRepoMock.findByName(TestContstants.TEST_NAME_NOT_FOUND))
		.thenReturn(Optional.of(savedAccountEntity1));
		Exception exception = assertThrows(AccountNotFoundException.class, () -> {
			accountService.getAccount(TestContstants.TEST_NAME_NOT_FOUND);
		});
		assertTrue(exception.getMessage().contains(ServiceExceptionConstants.ACCOUNT_NOT_FOUND));
	}

	@Test
	void testUpdateAccount() throws AccountNotFoundException, AccountAlreadyExistException {
		when(accountRepoMock.findByHostReferenceAndDeletedFalse(TestContstants.TEST_HOST_REFERENCE_1))
		.thenReturn(Optional.of(savedAccountEntity1));
	
		savedAccountEntity1.setName(TestContstants.TEST_NAME_1 + "UPDATE");
		savedAccountEntity1.setDescription(TestContstants.TEST_DESCRIPTION_1 + "UPDATE");
		AccountEntity account = accountService.updateAccount(savedAccountEntity1);
		assertEquals(TestContstants.TEST_NAME_1 + "UPDATE", account.getName());
		assertEquals(TestContstants.TEST_DESCRIPTION_1 + "UPDATE", account.getDescription());
	}

	@Test
	void testUpdateAccountThrowsException() {
		when(accountRepoMock.findByHostReferenceAndDeletedFalse(TestContstants.TEST_HOST_REFERENCE_1))
		.thenReturn(Optional.of(savedAccountEntity1));
		
		when(accountRepoMock.findByName(TestContstants.TEST_NAME_ALREADY_EXIST))
		.thenReturn(Optional.of(savedAccountEntity1));
		savedAccountEntity1.setName(TestContstants.TEST_NAME_ALREADY_EXIST);
		Exception exception = assertThrows(AccountAlreadyExistException.class, () -> {
			accountService.updateAccount(savedAccountEntity1);
		});
		assertEquals(ServiceExceptionConstants.DIFFERENT_ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME,
				exception.getMessage());
	}

	@Test
	void testSaveAccount() throws AccountAlreadyExistException {
		when(accountRepoMock.save(savedAccountEntity1)).thenReturn(savedAccountEntity1);
		AccountEntity account = accountService.saveAccount(accountEntity1);
		assertEquals(account.getName(), savedAccountEntity1.getName());
		assertEquals(account.getHostReference(), savedAccountEntity1.getHostReference());
		assertEquals(account.getDeleted(), savedAccountEntity1.getDeleted());
		assertEquals(account.getDescription(), savedAccountEntity1.getDescription());
		assertEquals(TestContstants.TEST_ID_1, savedAccountEntity1.getId());
	}

	@Test
	void testSaveAccountThrowException() {
		when(accountRepoMock.findByName(TestContstants.TEST_NAME_ALREADY_EXIST))
		.thenReturn(Optional.of(savedAccountEntity1));
		savedAccountEntity1.setName(TestContstants.TEST_NAME_ALREADY_EXIST);
		Exception exception = assertThrows(AccountAlreadyExistException.class, () -> {
			accountService.saveAccount(savedAccountEntity1);
		});
		assertTrue(exception.getMessage().contains(ServiceExceptionConstants.ACCOUNT_ALREADY_EXIST_WITH_THAT_NAME));
	}

	@Test
	void testDeleteAccount() throws AccountNotFoundException {
		when(accountRepoMock.findByHostReferenceAndDeletedFalse(TestContstants.TEST_HOST_REFERENCE_1))
		.thenReturn(Optional.of(savedAccountEntity1));

		assertEquals(ServiceConstants.DELETING_ACCOUNT_WAS_SUCCESSFUL,
				accountService.deleteAccount(savedAccountEntity1.getHostReference()));
	}

	@Test
	void testDeleteAccountThrowsException() {
		savedAccountEntity1.setDeleted(true);
		Exception exception = assertThrows(AccountNotFoundException.class, () -> {
			accountService.deleteAccount(savedAccountEntity1.getHostReference());
		});
		assertTrue(exception.getMessage().contains(ServiceExceptionConstants.ACCOUNT_NOT_FOUND));
	}
}
