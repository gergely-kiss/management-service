package uk.kissgergely.managementservice.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.api.exceptions.ControllerExceptionConstants;
import uk.kissgergely.managementservice.dtos.AccountDTO;
import uk.kissgergely.managementservice.exceptions.AccountServiceException;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.vos.AccountVO;

@Service
public class AccountControllerServiceImpl implements AccountControllerService {

	AccountService accountService;

	@Autowired
	public AccountControllerServiceImpl(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public List<AccountVO> getAllAccounts() {
		return accountService.getAllAccounts().stream().map(accountEntity -> {
			return new AccountDTO(accountEntity).getAccountVO();
		}).collect(Collectors.toList());
	}

	@Override
	public AccountVO getAccountById(String id) throws AccountControllerException {		
		try {
			return new AccountDTO(accountService.getAccount(id)).getAccountVO();
		} catch (AccountServiceException e) {
			throw new AccountControllerException(ControllerExceptionConstants.ACCOUNT_EXCEPTION);
		}
	}

	@Override
	public AccountVO createAccount(AccountVO account) throws AccountControllerException {
		try {
			return new AccountDTO(accountService.updateOrSaveAccount(new AccountDTO(account).getAccountEntity())).getAccountVO();
		} catch (AccountServiceException e) {
			throw new AccountControllerException(ControllerExceptionConstants.ACCOUNT_EXCEPTION);
		}
	}

	@Override
	public AccountVO updateAccount(AccountVO account) throws AccountControllerException {
		try {
			return new AccountDTO(accountService.updateOrSaveAccount(new AccountDTO(account).getAccountEntity())).getAccountVO();
		} catch (AccountServiceException e) {
			throw new AccountControllerException(ControllerExceptionConstants.ACCOUNT_EXCEPTION);
		}
	}

	@Override
	public AccountVO deleteAccount(String hostReference) throws AccountControllerException {
		try {
			return new AccountDTO(accountService.deleteAccount(hostReference)).getAccountVO();
		} catch (AccountServiceException e) {
			throw new AccountControllerException(ControllerExceptionConstants.ACCOUNT_EXCEPTION);
		}
	}

}
