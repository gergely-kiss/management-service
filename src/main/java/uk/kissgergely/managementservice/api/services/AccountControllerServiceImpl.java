package uk.kissgergely.managementservice.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.api.exceptions.AccountAlreadyExistControllerException;
import uk.kissgergely.managementservice.api.exceptions.AccountNotFoundControllerException;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.dtos.AccountDTO;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;

@Service
public class AccountControllerServiceImpl implements AccountControllerService {

	AccountService accountService;

	private static final Logger LOG = LoggerFactory.getLogger(AccountControllerServiceImpl.class);

	@Autowired
	public AccountControllerServiceImpl(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public List<AccountResponse> getAllAccounts() throws AccountNotFoundControllerException {
		LOG.info("getAllAccounts: called");
		List<AccountResponse> accountList = accountService.getAllAccounts().stream()
				.map(AccountDTO::transferEntityToResponse).collect(Collectors.toList());
		LOG.info("getAllAccounts: accountList {}", accountList);
		if (accountList.isEmpty()) {
			throw new AccountNotFoundControllerException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.NO_ACCOUNT_FOUND);
		} else {
			return accountList;
		}
	}

	@Override
	public AccountResponse getAccountById(String id) throws AccountNotFoundControllerException {
		LOG.info("getAccountById: was called with id {}", id);
		try {
			return AccountDTO.transferEntityToResponse(accountService.getAccount(id));
		} catch (AccountNotFoundException e) {
			throw new AccountNotFoundControllerException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		}
	}

	@Override
	public AccountResponse createAccount(String id, AccountRequest accountRequest)
			throws AccountAlreadyExistControllerException {
		LOG.info("createAccount: called with account {}", accountRequest);
		try {
			return AccountDTO.transferEntityToResponse(accountService.saveAccount(AccountDTO.transferRequestToEntity(accountRequest)));
		} catch (AccountAlreadyExistException e) {
			throw new AccountAlreadyExistControllerException(HttpStatus.CONFLICT,
					ControllerResponseConstants.ACCOUNT_ALREADY_EXIST);
		}
	}

	@Override
	public AccountResponse updateAccount(String id, AccountRequest accountRequest)
			throws AccountNotFoundControllerException, AccountAlreadyExistControllerException {
		LOG.info("updateAccount : called with account {}", accountRequest);
		try {
			return AccountDTO.transferEntityToResponse(accountService.updateAccount(AccountDTO.transferRequestToEntity(accountRequest)));
		} catch (AccountNotFoundException e) {
			throw new AccountNotFoundControllerException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		} catch (AccountAlreadyExistException e) {
			throw new AccountAlreadyExistControllerException(HttpStatus.CONFLICT,
					ControllerResponseConstants.ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
		}
	}

	@Override
	public String deleteAccount(String id) throws AccountNotFoundControllerException {
		LOG.info("deleteAccount: called with id {}", id);
		try {

			accountService.deleteAccount(id);
			return ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY;
		} catch (AccountNotFoundException e) {
			throw new AccountNotFoundControllerException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		}
	}

}
