package uk.kissgergely.managementservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.kissgergely.managementservice.entities.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

	Optional<AccountEntity> findByHostReference(String id);
	List<AccountEntity> findByDeletedFalse();

}
