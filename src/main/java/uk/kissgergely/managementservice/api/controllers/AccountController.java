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
import uk.kissgergely.managementservice.api.exceptions.AccountAlreadyExistControllerException;
import uk.kissgergely.managementservice.api.exceptions.AccountNotFoundControllerException;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.api.services.AccountControllerService;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;

@RestController
@RequestMapping(ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH)
public class AccountController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	AccountControllerService accountControllerService;

	@Autowired
	public AccountController(AccountControllerService accountControllerService) {
		this.accountControllerService = accountControllerService;
	}

	@GetMapping
	@ApiResponses(value = { @ApiResponse(code = 404, message = ControllerResponseConstants.NO_ACCOUNT_FOUND) })
	public ResponseEntity<List<AccountResponse>> getAccountList() throws AccountNotFoundControllerException {
		return  new ResponseEntity<>(accountControllerService.getAllAccounts(), HttpStatus.OK);
	}

	@GetMapping(ControllerConstants.SLASH_ID)
	@ApiResponses(value = { @ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND) })
	public ResponseEntity<AccountResponse> getAccount(@ApiParam(value = "Id for the account", required = true) @PathVariable String id)
			throws AccountNotFoundControllerException {
		LOG.info("{}", id);
		return new ResponseEntity<>(accountControllerService.getAccountById(id), HttpStatus.OK);
	}

	@PostMapping(ControllerConstants.SLASH_ID)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = { @ApiResponse(code = 201, message = ControllerResponseConstants.CREATED),
			@ApiResponse(code = 409, message = ControllerResponseConstants.ACCOUNT_ALREADY_EXIST) })
	public ResponseEntity<AccountResponse> addAccount(
			@ApiParam(value = "Id for the account", required = true) @PathVariable String id,
			@RequestBody AccountRequest accountRequest)
			throws AccountAlreadyExistControllerException {
		return new ResponseEntity<>(accountControllerService.createAccount(id, accountRequest), HttpStatus.CREATED);
	}

	@PutMapping(ControllerConstants.SLASH_ID)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = ControllerResponseConstants.RESOURCE_UPDATED_SUCCESSFULLY),
			@ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND),
			@ApiResponse(code = 409, message = ControllerResponseConstants.ACCOUNT_ALREADY_EXIST) })

	public ResponseEntity<AccountResponse> updateAccount(
			@ApiParam(value = "Id for the account", required = true) @PathVariable String id,
			@RequestBody AccountRequest accountRequest)
			throws AccountAlreadyExistControllerException, AccountNotFoundControllerException {
		return new ResponseEntity<>(accountControllerService.updateAccount(id, accountRequest), HttpStatus.valueOf(204));
	}

	@DeleteMapping(ControllerConstants.SLASH_ID)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY),
			@ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND) })
	public ResponseEntity<String> deletAccount(
			@ApiParam(value = "Id for the account", required = true) @PathVariable String id)
			throws AccountNotFoundControllerException {
		LOG.info("{}", id);
		return new ResponseEntity<>(accountControllerService.deleteAccount(id), HttpStatus.OK);
	}
}
