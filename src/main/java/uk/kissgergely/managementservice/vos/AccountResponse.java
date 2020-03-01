package uk.kissgergely.managementservice.vos;

public class AccountResponse extends AccountRequest{
	
	private Integer id;

	public AccountResponse() {
	}

	public AccountResponse(Integer id, String accountName, String accountDescription) {
		super(accountName, accountDescription);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
