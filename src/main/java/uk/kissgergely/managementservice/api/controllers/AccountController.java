package uk.kissgergely.managementservice.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import uk.kissgergely.managementservice.APIConstants;
import uk.kissgergely.managementservice.vos.AccountVO;

@RestController
@RequestMapping(APIConstants.API_ROOT + APIConstants.ACCOUNT_PATH)
public class AccountController {

	@GetMapping("/")
	@ApiOperation(value = "Return all acounts", notes = "Return all saved accounts")
	public List<AccountVO> getAccountList() {
		return new ArrayList<AccountVO>();
	}

	@GetMapping("/{id}")
	public AccountVO getAccount(@ApiParam(value = "Id for the account", required = true)@PathVariable Integer id) {
		return new AccountVO();
	}

	@PostMapping("/")
	public AccountVO addAccount(@RequestBody AccountVO account) {
		return account;
	}
}
