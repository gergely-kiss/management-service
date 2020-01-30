package uk.kissgergely.managementservice.data.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = JpaConstants.ACCOUNT)
public class AccountEntity {
	
	@Id
	@GeneratedValue
	@Column(name = JpaConstants.ID, nullable = false)
	private Integer id;
	
	@Column(name = JpaConstants.NAME)
	private String name;
	
	@Column(name = JpaConstants.DESCRIPTION)
	private String description;
	
	@Column(name = JpaConstants.HOST_REFERENCE)
	private String hostReference;
	
	@Column(name = JpaConstants.DELETED)
	private Boolean deleted;
		
	public AccountEntity() {
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(String name, String description) {
		this.name = name;
		this.description = description;
		this.hostReference = UUID.randomUUID().toString();
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(String name, String description, String hostReference) {
		this.name = name;
		this.description = description;
		this.hostReference = hostReference;
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(Integer id, String name, String description, String hostReference) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.hostReference = hostReference;
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
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
	    builder.append(", name=");
	    builder.append(name);
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
