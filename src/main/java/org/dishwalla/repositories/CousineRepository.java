package org.dishwalla.repositories;

import org.dishwalla.models.Cousine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CousineRepository extends CrudRepository<Cousine, Integer>{

	Iterable<Cousine> findByNameContaining(String searchText);

}
