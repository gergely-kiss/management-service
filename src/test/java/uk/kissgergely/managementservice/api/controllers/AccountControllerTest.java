package uk.kissgergely.managementservice.api.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
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

import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.api.services.AccountControllerService;
import uk.kissgergely.managementservice.services.TestContstants;
import uk.kissgergely.managementservice.unittesttools.TestUntils;
import uk.kissgergely.managementservice.vos.AccountVO;

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountControllerService accountControllerService;

	private AccountVO accountVO1;
	private AccountVO accountVO2;
	private List<AccountVO> accountList;

	private static final String URL = ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
		accountVO1 = new AccountVO(TestContstants.ACCOUNT_HOSTREF_1, TestContstants.ACCOUNT_NAME_1,
				TestContstants.ACCOUNT_DESC_1);
		accountVO2 = new AccountVO(TestContstants.ACCOUNT_HOSTREF_2, TestContstants.ACCOUNT_NAME_2,
				TestContstants.ACCOUNT_DESC_2);

	}

	@Test
	void getAll() throws Exception {
		accountList = new ArrayList<>();
		accountList.add(accountVO1);
		accountList.add(accountVO2);
		when(accountControllerService.getAllAccounts()).thenReturn(accountList);

		this.mockMvc.perform(get(URL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(TestUntils.asJsonString(accountList)));
	}

	@Test
	void getAllException() throws Exception {
		when(accountControllerService.getAllAccounts()).thenThrow(
				new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));

		this.mockMvc.perform(get(URL).characterEncoding("UTF-8")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void getById() throws Exception {
		when(accountControllerService.getAccountById(TestContstants.ACCOUNT_HOSTREF_1)).thenReturn(accountVO1);
		this.mockMvc.perform(get(URL + "/" + TestContstants.ACCOUNT_HOSTREF_1).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(TestUntils.asJsonString(accountVO1)));
	}

	@Test
	void getByIdException() throws Exception {
		when(accountControllerService.getAccountById(TestContstants.ACCOUNT_HOSTREF_NOT_FOUND)).thenThrow(
				new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));

		this.mockMvc.perform(get(URL + "/" + TestContstants.ACCOUNT_HOSTREF_NOT_FOUND).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void deleteById() throws Exception {
		when(accountControllerService.deleteAccount(TestContstants.ACCOUNT_HOSTREF_1))
				.thenReturn(ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY);
		this.mockMvc.perform(delete(URL + "/" + TestContstants.ACCOUNT_HOSTREF_1).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY));
	}

	@Test
	void deleteByIdException() throws Exception {
		when(accountControllerService.deleteAccount(TestContstants.ACCOUNT_HOSTREF_NOT_FOUND)).thenThrow(
				new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));
		this.mockMvc.perform(delete(URL + "/" + TestContstants.ACCOUNT_HOSTREF_NOT_FOUND).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void create() throws Exception {
		AccountVO accountVO1NotSaved = new AccountVO();
		accountVO1NotSaved.setAccountName(TestContstants.ACCOUNT_NAME_1);
		when(accountControllerService.createAccount(any(AccountVO.class))).thenReturn(accountVO1);
		this.mockMvc
				.perform(post(URL + "/").contentType(MediaType.APPLICATION_JSON)
						.content(TestUntils.asJsonString(accountVO1NotSaved)).characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(content().string(TestUntils.asJsonString(accountVO1)));
	}

	@Test
	void createException() throws Exception {
		when(accountControllerService.createAccount(any(AccountVO.class))).thenThrow(
				new AccountControllerException(HttpStatus.CONFLICT, ControllerResponseConstants.ACCOUNT_ALREADY_EXIST));
		this.mockMvc.perform(post(URL + "/").contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountVO1))
				.characterEncoding("UTF-8")).andExpect(status().isConflict());
	}

	@Test
	void update() throws Exception {
		when(accountControllerService.updateAccount(any(AccountVO.class))).thenReturn(accountVO2);
		this.mockMvc
				.perform(put(URL + "/").contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountVO1))
						.characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().string(TestUntils.asJsonString(accountVO2)));
	}
	
	@Test
	void updateException() throws Exception {
		when(accountControllerService.updateAccount(any(AccountVO.class))).thenThrow(
				new AccountControllerException(HttpStatus.CONFLICT, ControllerResponseConstants.ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME));
		this.mockMvc
				.perform(put(URL + "/").contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountVO1))
						.characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isConflict());
	}
	
	@Test
	void updateExceptionAccountNotFound() throws Exception {
		when(accountControllerService.updateAccount(any(AccountVO.class))).thenThrow(
				new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.ACCOUNT_NOT_FOUND));
		this.mockMvc
				.perform(put(URL + "/").contentType(MediaType.APPLICATION_JSON).content(TestUntils.asJsonString(accountVO1))
						.characterEncoding("UTF-8"))
				.andDo(print()).andExpect(status().isNotFound());
	}

}
