package uk.kissgergely.managementservice.data.entities;

import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.*;

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

	@Column(name = JpaConstants.DELETED)
	private Boolean deleted;

	@Column(name = JpaConstants.BALANCE, nullable = false)
	private Double balance;

	@OneToMany
	private Set<TagEntity> tagEntitySet;

	public AccountEntity() {
		this.balance = 0D;
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(String name, String description) {
		this.name = name;
		this.description = description;
		this.balance = 0D;
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.balance = 0D;
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(Integer id, String name, String description, Double balance) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.balance = balance == null ? 0D : balance;
		this.deleted = JpaConstants.DELETED_DEFAULT;
	}

	public AccountEntity(Integer id, String name, String description, Double balance, Set<TagEntity> tagEntitySet) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.balance = balance == null ? 0D : balance;
		this.tagEntitySet = tagEntitySet;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Set<TagEntity> getTagEntitySet() {
		return tagEntitySet;
	}

	public void setTagEntitySet(Set<TagEntity> tagEntitySet) {
		this.tagEntitySet = tagEntitySet;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", AccountEntity.class.getSimpleName() + "[", "]")
				.add(JpaConstants.ID + id)
				.add(JpaConstants.NAME + name + "'")
				.add(JpaConstants.DESCRIPTION + description + "'")
				.add(JpaConstants.DELETED + deleted)
				.add(JpaConstants.BALANCE + balance)
				.add(JpaConstants.TAG_ENTITY_SET + tagEntitySet)
				.toString();
	}
}
