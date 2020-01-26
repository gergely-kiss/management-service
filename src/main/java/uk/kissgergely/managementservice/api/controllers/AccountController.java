package uk.kissgergely.managementservice.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.kissgergely.managementservice.api.exceptions.AccountControllerException;
import uk.kissgergely.managementservice.api.services.AccountControllerService;
import uk.kissgergely.managementservice.resources.ControllerConstants;
import uk.kissgergely.managementservice.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.vos.AccountVO;

@RestController
@RequestMapping(ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH)
public class AccountController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	AccountControllerService accountControllerService;

	@Autowired
	public AccountController(AccountControllerService accountControllerService) {
		this.accountControllerService = accountControllerService;
	}

	@GetMapping("/")
	@ApiResponses(value = { @ApiResponse(code = 404, message = ControllerResponseConstants.NO_ACCOUNT_FOUND) })
	public List<AccountVO> getAccountList() throws AccountControllerException {
		List<AccountVO> accountVOList = accountControllerService.getAllAccounts();
		LOG.info("getAccountList: accountVOList {}", accountVOList);
		return accountVOList;
	}

	@GetMapping("/{id}")
	@ApiResponses(value = { @ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND) })
	public AccountVO getAccount(@ApiParam(value = "Id for the account", required = true) @PathVariable String id)
			throws AccountControllerException {
		LOG.info("{}", id);
		return accountControllerService.getAccountById(id);
	}

	@PostMapping("/")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = { @ApiResponse(code = 201, message = ControllerResponseConstants.CREATED),
			@ApiResponse(code = 409, message = ControllerResponseConstants.ACCOUNT_ALREADY_EXIST) })
	public ResponseEntity<AccountVO> addAccount(@RequestBody AccountVO account) {
		LOG.info("addAccount: called with {}", account);
		AccountVO accountVO = accountControllerService.createAccount(account);
		LOG.info("addAccount: account added {} ", account);
		return new ResponseEntity<>(accountVO, HttpStatus.CREATED);
	}

	@PutMapping("/")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = ControllerResponseConstants.RESOURCE_UPDATED_SUCCESSFULLY),
			@ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND),
			@ApiResponse(code = 409, message = ControllerResponseConstants.ACCOUNT_ALREADY_EXIST) })

	public ResponseEntity<AccountVO> updateAccount(@RequestBody AccountVO account) throws AccountControllerException {
		AccountVO accountVO = accountControllerService.updateAccount(account);
		LOG.info("updateAccount: account updated {} ", account);
		return new ResponseEntity<>(accountVO, HttpStatus.valueOf(204));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY),
			@ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND) })
	public ResponseEntity<String> deletAccount(
			@ApiParam(value = "Id for the account", required = true) @PathVariable String id)
			throws AccountControllerException {
		LOG.info("{}", id);
		return ResponseEntity.ok(accountControllerService.deleteAccount(id));
	}
}
