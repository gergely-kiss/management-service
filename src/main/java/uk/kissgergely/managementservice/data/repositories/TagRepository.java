package uk.kissgergely.managementservice.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.kissgergely.managementservice.data.entities.TagEntity;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {
}
