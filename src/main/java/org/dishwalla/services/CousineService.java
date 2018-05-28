package org.dishwalla.services;

import org.dishwalla.models.Cousine;
import org.dishwalla.repositories.CousineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CousineService {

	@Autowired
	private CousineRepository cousineRepo;
	
	public Iterable<Cousine> findAll(){
		return cousineRepo.findAll();
	}

	public Iterable<Cousine> findByNameContaining(String searchText) {
		return cousineRepo.findByNameContaining(searchText);
	}
	
	
}
