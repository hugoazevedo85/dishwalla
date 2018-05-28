package org.dishwalla.services;

import org.dishwalla.models.Store;
import org.dishwalla.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepo;
	
	public Iterable<Store> findAll(){
		return storeRepo.findAll();
	}
	
}
