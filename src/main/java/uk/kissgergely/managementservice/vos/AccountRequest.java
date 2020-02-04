package uk.kissgergely.managementservice.vos;


public class AccountRequest {
	
	private String accountName;
	
	private String accountDescription;

	public AccountRequest() {
		
	}

	public AccountRequest(String accountName, String accountDescription) {
		this.accountName = accountName;
		this.accountDescription = accountDescription;
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
		builder.append("AccountRequest [accountName=");
		builder.append(accountName);
		builder.append(", accountDescription=");
		builder.append(accountDescription);
		builder.append("]");
		return builder.toString();
	}

}
		
