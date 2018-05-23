package org.dishwalla.controllers;

import org.dishwalla.dtos.inputs.LoginRequest;
import org.dishwalla.models.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

	@RequestMapping("/api/teste")
	public LoginRequest teste() {
		LoginRequest req = new LoginRequest();
		req.setEmail("hugo.haa");
		req.setPassword("123");
		return req;
	}
	
	@RequestMapping("/api/public/teste")
	public LoginRequest teste2() {
		LoginRequest req = new LoginRequest();
		req.setEmail("publico");
		req.setPassword("123");
		return req;
	}
}
