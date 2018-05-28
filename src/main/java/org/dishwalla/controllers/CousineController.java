package org.dishwalla.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.dishwalla.dtos.outputs.CousineResponse;
import org.dishwalla.models.Cousine;
import org.dishwalla.services.CousineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Cousine")
public class CousineController {

	@Autowired
	private CousineService cousineService;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@RequestMapping(method=RequestMethod.GET)
	public List<CousineResponse> getCousines(){
		Iterable<Cousine> cousinesList = cousineService.findAll();
		List<CousineResponse> toReturn = StreamSupport.stream(cousinesList.spliterator(), false)
										 .map(cousine -> modelMapper.map(cousine, CousineResponse.class))
										 .collect(Collectors.toList());
		return toReturn;
	}
	
	@RequestMapping(value="/search/{searchText}", method=RequestMethod.GET)
	public List<CousineResponse> getCousinesById(@PathVariable("searchText") String searchText){
		Iterable<Cousine> cousinesList = cousineService.findByNameContaining(searchText);
		List<CousineResponse> toReturn = StreamSupport.stream(cousinesList.spliterator(), false)
										 .map(cousine -> modelMapper.map(cousine, CousineResponse.class))
										 .collect(Collectors.toList());
		return toReturn;
	}
	
}
