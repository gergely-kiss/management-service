package uk.kissgergely.managementservice.vos;

public class AccountResponse extends AccountRequest{
	
	private String id;

	public AccountResponse() {
	}
	
	

	public AccountResponse(String id, String accountName, String accountDescription) {
		super(accountName, accountDescription);
		this.id = id;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountResponse [id=");
		builder.append(id);
		builder.append(", getAccountName()=");
		builder.append(getAccountName());
		builder.append(", getAccountDescription()=");
		builder.append(getAccountDescription());
		builder.append("]");
		return builder.toString();
	}

}
