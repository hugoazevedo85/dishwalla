package org.dishwalla.seguranca.filtros;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dishwalla.dtos.inputs.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsernamePasswordCredentialsFilter extends AbstractAuthenticationProcessingFilter {

	private final ObjectMapper objectMapper;
	
	public UsernamePasswordCredentialsFilter(String defaultFilterProcessesUrl, ObjectMapper objectMapper, AuthenticationManager manager) {
		super(defaultFilterProcessesUrl);
		this.setAuthenticationManager(manager);
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		
		LoginRequest loginDTO = objectMapper.readValue(request.getReader(), LoginRequest.class);
		UsernamePasswordAuthenticationToken userAuthObject = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
		return this.getAuthenticationManager().authenticate(userAuthObject);
		
	}
}
