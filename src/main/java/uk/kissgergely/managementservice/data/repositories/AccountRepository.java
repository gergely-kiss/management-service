package uk.kissgergely.managementservice.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.kissgergely.managementservice.data.entities.AccountEntity;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

	/**
	 * @param id
	 * @return Optional<AccountEntity> were the deleted flag is set to false
	 */
    Optional<AccountEntity> findByIdAndDeletedFalse(Integer id);


	/**
	 * @return
	 */
    List<AccountEntity> findAllByDeletedFalse();

	/**
	 * @param accountName
	 * @return Optional<AccountEntity> were the deleted flag is set to false
	 */
    Optional<AccountEntity> findByNameAndDeletedFalse(String accountName);

}