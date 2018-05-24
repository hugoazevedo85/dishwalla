package org.dishwalla.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dishwalla.seguranca.tokens.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		
		 String login = auth.getName();
		 String token = tokenService.createToken(login);
		 Map<String,String> retorno = new HashMap<String,String>();
		 retorno.put("token", token);
		 
		 response.setStatus(HttpStatus.OK.value());
	     response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		 
	     mapper.writeValue(response.getWriter(), retorno); 
	}

}
