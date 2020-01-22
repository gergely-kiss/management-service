package uk.kissgergely.managementservice.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import uk.kissgergely.managementservice.APIConstants;
import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.api.services.AccountControllerService;
import uk.kissgergely.managementservice.vos.AccountVO;

@RestController
@RequestMapping(APIConstants.API_ROOT + APIConstants.ACCOUNT_PATH)
public class AccountController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class); 
	
	AccountControllerService accountControllerService;
	

	@Autowired
	public AccountController(AccountControllerService accountControllerService) {
		this.accountControllerService = accountControllerService;
	}

	@GetMapping("/")
	@ApiOperation(value = "Return all acounts", notes = "Return all saved accounts")
	public List<AccountVO> getAccountList() {
		return accountControllerService.getAllAccounts();
	}

	@GetMapping("/{id}")
	public AccountVO getAccount(@ApiParam(value = "Id for the account", required = true)@PathVariable String id) throws AccountControllerException {
		LOG.info("{}", id);
		return accountControllerService.getAccountById(id);
	}

	@PostMapping("/")
	public AccountVO addAccount(@RequestBody AccountVO account) throws AccountControllerException {
		LOG.info("addAccount called with: {}", account);
		return accountControllerService.createAccount(account);
	}
	
	@PutMapping("/")
	public AccountVO updateAccount(@RequestBody AccountVO account) throws AccountControllerException {
		return accountControllerService.updateAccount(account);
	}
	
	@DeleteMapping("/{id}")
	public AccountVO deletAccount(@ApiParam(value = "Id for the account", required = true)@PathVariable String id) throws AccountControllerException {
		LOG.info("{}", id);
		return accountControllerService.deleteAccount(id);
	}
}
