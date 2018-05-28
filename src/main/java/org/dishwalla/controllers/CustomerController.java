package org.dishwalla.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.dishwalla.dtos.inputs.CustomerCreationRequest;
import org.dishwalla.models.Customer;
import org.dishwalla.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Customer")
public class CustomerController {


	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerCreationRequest customerDTO, HttpServletResponse response) throws ParseException {
		Customer customer = this.convertToEntity(customerDTO);
		customerService.saveCustomer(customer);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	private Customer convertToEntity(CustomerCreationRequest customerDTO) throws ParseException {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		customer.setCreation(customerDTO.getDateConverted(customerDTO.getCreation()));
		return customer;
	}
	
}
