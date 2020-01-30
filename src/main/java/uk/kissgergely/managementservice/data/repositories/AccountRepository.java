package uk.kissgergely.managementservice.data.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.kissgergely.managementservice.data.entities.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

	Optional<AccountEntity> findByHostReferenceAndDeletedFalse(String id);
	List<AccountEntity> findByDeletedFalse();
	Optional<AccountEntity> findByName(String accountName);

}
