package org.dishwalla.repositories;

import org.dishwalla.models.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer>{
	
}
