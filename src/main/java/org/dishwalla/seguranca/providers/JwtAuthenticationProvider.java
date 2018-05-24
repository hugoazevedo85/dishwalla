package org.dishwalla.seguranca.providers;

import java.util.Collections;

import org.dishwalla.seguranca.models.AuthUser;
import org.dishwalla.seguranca.tokens.JwtAuthenticationToken;
import org.dishwalla.seguranca.tokens.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		JwtAuthenticationToken authToken = (JwtAuthenticationToken) auth;
		String token = (String) authToken.getCredentials();
		Jws<Claims> tokenClaims = new TokenService().getTokenClaims(token);
		String subject = tokenClaims.getBody().getSubject();
		AuthUser authUser = new AuthUser(subject);
		return new JwtAuthenticationToken(authUser,Collections.emptyList());
	}

	@Override
	public boolean supports(Class<?> auth) {
		return JwtAuthenticationToken.class.isAssignableFrom(auth);
	}

}
