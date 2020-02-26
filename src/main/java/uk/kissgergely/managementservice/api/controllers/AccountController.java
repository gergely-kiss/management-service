package uk.kissgergely.managementservice.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.server.ResponseStatusException;
import uk.kissgergely.managementservice.api.resources.ControllerConstants;
import uk.kissgergely.managementservice.api.resources.ControllerResponseConstants;
import uk.kissgergely.managementservice.dtos.AccountDTO;
import uk.kissgergely.managementservice.services.AccountService;
import uk.kissgergely.managementservice.services.exceptions.AccountAlreadyExistException;
import uk.kissgergely.managementservice.services.exceptions.AccountNotFoundException;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;

@RestController
@RequestMapping(ControllerConstants.API_ROOT + ControllerConstants.ACCOUNT_PATH)
public class AccountController {

	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	@ApiResponses(value = { @ApiResponse(code = 404, message = ControllerResponseConstants.NO_ACCOUNT_FOUND) })
	public ResponseEntity<List<AccountResponse>> getAccountList() {
		return new ResponseEntity<>(accountService.getAllAccounts().stream().map(AccountDTO::transferEntityToResponse)
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping(ControllerConstants.SLASH_ID)
	@ApiResponses(value = { @ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND) })
	public ResponseEntity<AccountResponse> getAccount(
			@ApiParam(value = "Id for the account", required = true) @PathVariable String id)
			throws ResponseStatusException {
		try {
			return new ResponseEntity<>(AccountDTO.transferEntityToResponse(accountService.getAccount(id).orElseThrow(null)),
					HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		}
	}

	@PostMapping(ControllerConstants.SLASH_ID)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = { @ApiResponse(code = 201, message = ControllerResponseConstants.CREATED),
			@ApiResponse(code = 409, message = ControllerResponseConstants.ACCOUNT_ALREADY_EXIST) })
	public ResponseEntity<AccountResponse> addAccount(@RequestBody AccountRequest accountRequest)
			throws ResponseStatusException {
		try {
			return new ResponseEntity<>(
					AccountDTO.transferEntityToResponse(
							accountService.saveAccount(AccountDTO.transferRequestToEntity(accountRequest)).orElseThrow(null)),
					HttpStatus.CREATED);
		} catch (AccountAlreadyExistException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					ControllerResponseConstants.ACCOUNT_ALREADY_EXIST);
		}
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
			throws ResponseStatusException {
		try {
			return new ResponseEntity<>(
					AccountDTO.transferEntityToResponse(
							accountService.updateAccount(AccountDTO.transferRequestToEntity(id, accountRequest)).orElseThrow(null)),
					HttpStatus.valueOf(204));
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		} catch (AccountAlreadyExistException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					ControllerResponseConstants.ACCOUNT_ALREADY_EXIST_WITH_THE_SAME_NAME);
		}
	}

	@DeleteMapping(ControllerConstants.SLASH_ID)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = ControllerResponseConstants.RESOURCE_DELETED_SUCCESSFULLY),
			@ApiResponse(code = 404, message = ControllerResponseConstants.ACCOUNT_NOT_FOUND) })
	public ResponseEntity<String> deletAccount(

			@ApiParam(value = "Id for the account", required = true) @PathVariable String id)
			throws ResponseStatusException {
		LOG.info("{}", id);
		try {
			return new ResponseEntity<>(accountService.deleteAccount(id), HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					ControllerResponseConstants.ACCOUNT_NOT_FOUND);
		}
	}

}
