package uk.kissgergely.managementservice.data.entities;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.StringJoiner;
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

	@Column(name = JpaConstants.CREATED, nullable =  false)
	private LocalDateTime created;

	@Column(name = JpaConstants.LAST_UPDATED, nullable =  false)
	private LocalDateTime lastUpdated;

	@Column(name = JpaConstants.MARKED_DELETED)
	private LocalDateTime markedDeleted;

	@OneToMany
	private Set<TagEntity> tagEntitySet;

	public AccountEntity() {
		initDefault(null);
	}

	public AccountEntity(String name, String description) {
		this.name = name;
		this.description = description;
		initDefault(null);
	}

	public AccountEntity(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		initDefault(null);
	}

	public AccountEntity(Integer id, String name, String description, Double balance) {
		this.id = id;
		this.name = name;
		this.description = description;
		initDefault(balance);
	}

	public AccountEntity(Integer id, String name, String description, Double balance, Set<TagEntity> tagEntitySet) {
		this.id = id;
		this.name = name;
		this.description = description;
		initDefault(balance);
		this.tagEntitySet = tagEntitySet;
	}

	private void initDefault(Double balance){
		this.balance = balance == null ? JpaConstants.BALANCE_DEFAULT : balance;
		this.deleted = JpaConstants.DELETED_DEFAULT;
		this.created = LocalDateTime.now();
		this.lastUpdated = LocalDateTime.now();
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public LocalDateTime getMarkedDeleted() {
		return markedDeleted;
	}

	public void setMarkedDeleted(LocalDateTime markedDeleted) {
		this.markedDeleted = markedDeleted;
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
				.add(JpaConstants.CREATED + created)
				.add(JpaConstants.LAST_UPDATED + lastUpdated)
				.add(JpaConstants.MARKED_DELETED + markedDeleted)
				.toString();
	}
}
