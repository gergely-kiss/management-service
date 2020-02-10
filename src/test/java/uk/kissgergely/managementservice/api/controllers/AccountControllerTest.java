package uk.kissgergely.managementservice.api.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import uk.kissgergely.managementservice.api.exceptions.AccountAlreadyExistControllerException;
import uk.kissgergely.managementservice.api.exceptions.AccountNotFoundControllerException;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.api.services.AccountControllerService;
import uk.kissgergely.managementservice.unittesttools.TestContstants;
import uk.kissgergely.managementservice.unittesttools.TestUntils;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;
@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountControllerService accountControllerService;

	private AccountRequest accountRequest;
	private AccountResponse accountResponse;
	
	private static final String URL = ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
		accountRequest = new AccountRequest(TestContstants.TEST_NAME_1,
				TestContstants.TEST_DESCRIPTION_1);
		accountResponse = new AccountResponse(TestContstants.TEST_HOST_REFERENCE_1, TestContstants.TEST_NAME_1,
				TestContstants.TEST_DESCRIPTION_1);

	}

	@Test
	void getAll() throws Exception {
		List<AccountResponse> accountList = new ArrayList<>();
		when(accountControllerService.getAllAccounts()).thenReturn(accountList);

		this.mockMvc.perform(get(URL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(TestUntils.asJsonString(accountList)));
	}

	@Test
	void getAllException() throws Exception {
		when(accountControllerService.getAllAccounts()).thenThrow(
				new AccountNotFoundControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));

		this.mockMvc.perform(get(URL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void getById() throws Exception {
		when(accountControllerService.getAccountById(TestContstants.TEST_HOST_REFERENCE_1)).thenReturn(accountResponse);
		this.mockMvc.perform(get(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(TestUntils.asJsonString(accountResponse)));
	}

	@Test
	void getByIdException() throws Exception {
		when(accountControllerService.getAccountById(TestContstants.TEST_HOST_REFERENCE_1)).thenThrow(
				new AccountNotFoundControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));

		this.mockMvc.perform(get(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void deleteById() throws Exception {
		when(accountControllerService.deleteAccount(TestContstants.TEST_HOST_REFERENCE_1))
				.thenReturn(ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY);
		this.mockMvc.perform(delete(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY));
	}

	@Test
	void deleteByIdException() throws Exception {
		when(accountControllerService.deleteAccount(TestContstants.TEST_HOST_REFERENCE_1)).thenThrow(
				new AccountNotFoundControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));
		this.mockMvc.perform(delete(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void create() throws Exception {
		when(accountControllerService.createAccount(eq(TestContstants.TEST_HOST_REFERENCE_1), any(AccountRequest.class))).thenReturn(accountResponse);
		this.mockMvc
				.perform(post(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).contentType(MediaType.APPLICATION_JSON)
						.content(TestUntils.asJsonString(accountRequest)).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(content().string(TestUntils.asJsonString(accountResponse)));
	}

	@Test
	void createException() throws Exception {
		when(accountControllerService.createAccount(eq(TestContstants.TEST_HOST_REFERENCE_1), any(AccountRequest.class))).thenThrow(
				new AccountAlreadyExistControllerException(HttpStatus.CONFLICT, ControllerResponseConstants.ACCOUNT_ALREADY_EXIST));
		this.mockMvc.perform(post(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountRequest))
				.characterEncoding("UTF-8")).andExpect(status().isConflict());
	}

	@Test
	void update() throws Exception {
		when(accountControllerService.updateAccount(eq(TestContstants.TEST_HOST_REFERENCE_1), any(AccountRequest.class))).thenReturn(accountResponse);
		this.mockMvc
				.perform(put(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountRequest))
						.characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().string(TestUntils.asJsonString(accountResponse)));
	}
	
	@Test
	void updateException() throws Exception {
		when(accountControllerService.updateAccount(eq(TestContstants.TEST_HOST_REFERENCE_1), any(AccountRequest.class))).thenThrow(
				new AccountAlreadyExistControllerException(HttpStatus.CONFLICT, ControllerResponseConstants.ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME));
		this.mockMvc
				.perform(put(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountRequest))
						.characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isConflict());
	}
	
	@Test
	void updateExceptionAccountNotFound() throws Exception {
		when(accountControllerService.updateAccount(eq(TestContstants.TEST_HOST_REFERENCE_1), any(AccountRequest.class))).thenThrow(
				new AccountNotFoundControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.ACCOUNT_NOT_FOUND));
		this.mockMvc
				.perform(put(URL + "/" + TestContstants.TEST_HOST_REFERENCE_1).contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountRequest))
						.characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isNotFound());
	}

}
