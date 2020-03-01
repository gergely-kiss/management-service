package uk.kissgergely.managementservice.dtos;

import uk.kissgergely.managementservice.data.entities.AccountEntity;
import uk.kissgergely.managementservice.vos.AccountRequest;
import uk.kissgergely.managementservice.vos.AccountResponse;

public class AccountDTO {

	private AccountDTO() {
	}

	public static AccountResponse transferEntityToResponse(AccountEntity accountEntity) {
		return new AccountResponse(accountEntity.getId(), accountEntity.getName(),
				accountEntity.getDescription());
	}

	public static AccountEntity transferRequestToEntity(AccountRequest accountRequest) {
		return new AccountEntity(accountRequest.getAccountName(), accountRequest.getAccountDescription());
	}
	
	public static AccountEntity transferRequestToEntity(Integer id, AccountRequest accountRequest) {
		return new AccountEntity(id, accountRequest.getAccountName(), accountRequest.getAccountDescription());
	}

}
