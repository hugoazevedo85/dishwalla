package org.dishwalla.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dishwalla.exceptions.JwtExpiredTokenException;
import org.dishwalla.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		if(authException instanceof UsernameNotFoundException || authException instanceof BadCredentialsException) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			GenericResponse genResponse = new GenericResponse("InvalidUserOrPassword",authException.getMessage());
			objectMapper.writeValue(response.getWriter(), genResponse);
		}
		
		if(authException instanceof JwtExpiredTokenException) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			GenericResponse genResponse = new GenericResponse("InvalidToken",authException.getMessage());
			objectMapper.writeValue(response.getWriter(), genResponse);
		}
		
		else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}

	}

}
