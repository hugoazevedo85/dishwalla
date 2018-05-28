package org.dishwalla.seguranca.configs;

import java.util.Arrays;
import java.util.List;

import org.dishwalla.seguranca.filtros.CorsFilter;
import org.dishwalla.seguranca.filtros.JwtCredentialsFilter;
import org.dishwalla.seguranca.filtros.UsernamePasswordCredentialsFilter;
import org.dishwalla.seguranca.providers.JwtAuthenticationProvider;
import org.dishwalla.seguranca.tokens.TokenService;
import org.dishwalla.seguranca.util.CaminhosIgnorados;
import org.dishwalla.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AuthenticationSuccessHandler authSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler authFailureHandler;
	
	@Autowired
	private TokenService tokenService;
	
	private final String API_PREFIX = "/api/v1";
	
	
	private final List<String> publicPath = 
			Arrays.asList(new String[] { API_PREFIX + "/Cousine",
										 API_PREFIX + "/Cousine/search/*",
										 API_PREFIX + "/Store"});
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
		auth.authenticationProvider(jwtAuthenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST,API_PREFIX + "/Customer/auth").permitAll()
			.antMatchers(publicPath.toArray(new String[publicPath.size()])).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new CorsFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(usernamePasswordCredentialsFilter(this.authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtCredentialsFilter(this.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
	public BCryptPasswordEncoder bryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	private DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(bryptPasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(customerService);
		return daoAuthenticationProvider;
	}
	
	
	private JwtAuthenticationProvider jwtAuthenticationProvider() {
		JwtAuthenticationProvider authProvider = new JwtAuthenticationProvider();
		return authProvider;
	}
	
	private UsernamePasswordCredentialsFilter usernamePasswordCredentialsFilter(AuthenticationManager manager) {
		UsernamePasswordCredentialsFilter usernamePasswordCredentialsFilter =
				new UsernamePasswordCredentialsFilter(API_PREFIX + "/Customer/auth",objectMapper,manager);
		usernamePasswordCredentialsFilter.setAuthenticationSuccessHandler(authSuccessHandler);
		usernamePasswordCredentialsFilter.setAuthenticationFailureHandler(authFailureHandler);
		return usernamePasswordCredentialsFilter;
	}
	
	private JwtCredentialsFilter jwtCredentialsFilter(AuthenticationManager manager) {
		CaminhosIgnorados ignorados = new CaminhosIgnorados(this.publicPath, "/api/**");
		JwtCredentialsFilter filter = new JwtCredentialsFilter(ignorados, tokenService);
		filter.setAuthenticationFailureHandler(authFailureHandler);
		filter.setAuthenticationManager(manager);
		return filter;

	}
	
	 
	
}
