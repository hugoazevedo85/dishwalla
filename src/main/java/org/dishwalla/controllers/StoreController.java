package org.dishwalla.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.dishwalla.dtos.outputs.StoreResponse;
import org.dishwalla.models.Store;
import org.dishwalla.services.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Store")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@RequestMapping(method=RequestMethod.GET)
	public List<StoreResponse> getStores(){
		Iterable<Store> stores = storeService.findAll();
		List<StoreResponse> toReturn = StreamSupport.stream(stores.spliterator(), false)
				.map(store -> modelMapper.map(store, StoreResponse.class))
				.collect(Collectors.toList());
		return toReturn;
	}
	
}
