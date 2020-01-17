package uk.kissgergely.managementservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.kissgergely.managementservice.entities.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<Integer, AccountEntity> {

}
