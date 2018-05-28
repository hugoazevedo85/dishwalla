package org.dishwalla.services;

import org.dishwalla.exceptions.EmailAlreadyExistException;
import org.dishwalla.models.Customer;
import org.dishwalla.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService{

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepo.findByEmail(email);
		if(customer == null)
			throw new UsernameNotFoundException("Email ou senha inválidos");
		return customer;
	}
	
	public Customer saveCustomer(Customer customer) {
		
		Customer findByEmail = customerRepo.findByEmail(customer.getEmail());
		if(findByEmail != null) {
			throw new EmailAlreadyExistException("Email já cadastrado");
		}
		
		customer.setPassword(encoder.encode(customer.getPassword()));
		Customer registered = customerRepo.save(customer);
		return registered;
	}

}
