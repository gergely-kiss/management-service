package uk.kissgergely.managementservice.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.dtos.AccountDTO;
import uk.kissgergely.managementservice.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.services.exceptions.ServiceException;
import uk.kissgergely.managementservice.vos.AccountVO;

@Service
public class AccountControllerServiceImpl implements AccountControllerService {

	AccountService accountService;

	@Autowired
	public AccountControllerServiceImpl(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public List<AccountVO> getAllAccounts() throws AccountControllerException{
		List<AccountVO> accountList = accountService.getAllAccounts().stream().map(accountEntity -> {
			return new AccountDTO(accountEntity).getAccountVO();
		}).collect(Collectors.toList());
		if (accountList.isEmpty()) {
			throw new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.NO_ACCOUNT_FOUND);
		} else {
			return accountList;
		}
	}

	@Override
	public AccountVO getAccountById(String id) throws AccountControllerException {
		try {
			return new AccountDTO(accountService.getAccount(id)).getAccountVO();
		} catch (AccountNotFoundException e) {
			throw new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		}
	}

	@Override
	public AccountVO createAccount(AccountVO account) throws AccountControllerException {
		try {
			return new AccountDTO(accountService.saveAccount(new AccountDTO(account).getAccountEntity()))
					.getAccountVO();
		} catch (ServiceException e) {
			throw new AccountControllerException(HttpStatus.CONFLICT,
					ControllerResponseConstants.ACCOUNT_ALREADY_EXIST);
		}
	}

	@Override
	public AccountVO updateAccount(AccountVO account) throws AccountControllerException {
		try {
			return new AccountDTO(accountService.updateAccount(new AccountDTO(account).getAccountEntity()))
					.getAccountVO();
		} catch (AccountNotFoundException e) {
			throw new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		} catch (AccountAlreadyExistException e) {
			throw new AccountControllerException(HttpStatus.CONFLICT,
					ControllerResponseConstants.ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
		}
	}

	@Override
	public String deleteAccount(String hostReference) throws AccountControllerException {
		try {
			return accountService.deleteAccount(hostReference);
		} catch (AccountNotFoundException e) {
			throw new AccountControllerException(HttpStatus.NOT_FOUND, ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		}
	}

}
