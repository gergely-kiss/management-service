package uk.kissgergely.managementservice.vos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "accounts what you have")
public class AccountVO {
	
	private String accountId;
	private String accountName;
	private String accountDescription;
	
	public AccountVO() {
	}
	
	public AccountVO(String accountId, String accountName, String accountDescription) {
		super();
		this.accountId = accountId;
		this.accountName = accountName;
		this.accountDescription = accountDescription;
	}

	@ApiModelProperty(notes = "unique id for the account")
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountDescription() {
		return accountDescription;
	}
	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}
	
}
