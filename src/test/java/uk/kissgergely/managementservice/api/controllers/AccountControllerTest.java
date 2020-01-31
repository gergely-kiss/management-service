package uk.kissgergely.managementservice.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.injection.scanner.MockScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.api.services.AccountControllerService;
import uk.kissgergely.managementservice.services.TestContstants;
import uk.kissgergely.managementservice.vos.AccountVO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

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

		this.mockMvc.perform(get(ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(TestContstants.ACCOUNT_LIST_RESPONSE_BODY));

	}
	
	@Test
	void getAllException() throws Exception {

		when(accountControllerService.getAllAccounts()).thenThrow(
				new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND));

		this.mockMvc.perform(get(ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH)).andDo(print())
				.andExpect(status().isNotFound());
				

	}

}
