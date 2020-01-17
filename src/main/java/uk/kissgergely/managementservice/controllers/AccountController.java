package uk.kissgergely.managementservice.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import uk.kissgergely.managementservice.APIConstants;
import uk.kissgergely.managementservice.daos.AccountDAO;

@RestController
@RequestMapping(APIConstants.API_ROOT + APIConstants.ACCOUNT_PATH)
public class AccountController {

	ConcurrentHashMap<Integer, AccountDAO> accounts = new ConcurrentHashMap<Integer, AccountDAO>();

	@GetMapping("/")
	@ApiOperation(value = "Return all acounts", notes = "Return all saved accounts")
	public List<AccountDAO> getAccountList() {
		return new ArrayList<AccountDAO>(accounts.values());
	}

	@GetMapping("/{id}")
	public AccountDAO getAccount(@ApiParam(value = "Id for the account", required = true)@PathVariable Integer id) {
		return accounts.get(id);
	}

	@PostMapping("/")
	public AccountDAO addAccount(@RequestBody AccountDAO account) {
		return accounts.put(account.getAccountId(), account);
	}
}
