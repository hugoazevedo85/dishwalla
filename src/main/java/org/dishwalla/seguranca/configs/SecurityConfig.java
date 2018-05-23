package org.dishwalla.seguranca.configs;

import org.dishwalla.seguranca.filtros.CorsFilter;
import org.dishwalla.seguranca.filtros.UsernamePasswordCredentialsFilter;
import org.dishwalla.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(customerService)
			.passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors()
			.and().authorizeRequests()
			.antMatchers(HttpMethod.GET,"/api/public/**").permitAll()
			.antMatchers(HttpMethod.POST,"/api/auth").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new CorsFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new UsernamePasswordCredentialsFilter("/api/auth",objectMapper,this.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
	public BCryptPasswordEncoder bryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
