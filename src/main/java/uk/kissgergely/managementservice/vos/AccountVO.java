package uk.kissgergely.managementservice.vos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "accounts what you have")
public class AccountVO {
	
	private String accountId;
	
	@ApiModelProperty(required = true, notes = "unique id for the account")
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

	public AccountVO(String accountName, String accountDescription) {
		super();
		this.accountName = accountName;
		this.accountDescription = accountDescription;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountVO [accountId=");
		builder.append(accountId);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", accountDescription=");
		builder.append(accountDescription);
		builder.append("]");
		return builder.toString();
	}
		
}
