package uk.kissgergely.managementservice.data.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "account_name", nullable = false)
	private String accountName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "host_reference")
	private String hostReference;
	
	@Column(name = "deleted")
	private Boolean deleted;
		
	public AccountEntity() {
		this.deleted = false;
	
	}

	public AccountEntity(String accountName, String description) {
		this.accountName = accountName;
		this.description = description;
		this.hostReference = UUID.randomUUID().toString();
		this.deleted = false;
	}

	public AccountEntity(Integer id, String accountName, String description, String hostReference) {
		super();
		this.id = id;
		this.accountName = accountName;
		this.description = description;
		this.hostReference = hostReference;
		this.deleted = false;
	}

	public AccountEntity(String accountName, String description, String id) {
		this.accountName = accountName;
		this.description = description;
		this.hostReference = id;
		this.deleted = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHostReference() {
		return hostReference;
	}

	public void setHostReference(String hostReference) {
		this.hostReference = hostReference;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountEntity [id=");
		builder.append(id);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", hostReference=");
		builder.append(hostReference);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append("]");
		return builder.toString();
	}	
}
