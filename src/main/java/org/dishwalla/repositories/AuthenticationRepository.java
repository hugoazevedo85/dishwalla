package org.dishwalla.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dishwalla.models.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository implements UserDetailsService{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		String query = "select c from Customer c where c.email = :login";
		List<Customer> users = em.createNamedQuery(query, Customer.class).getResultList();
		if(users.isEmpty()) {
			throw new UsernameNotFoundException("User with that login not found");
		}
		return users.get(0);
	}

}
