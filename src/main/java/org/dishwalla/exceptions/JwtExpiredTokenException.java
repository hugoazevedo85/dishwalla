package org.dishwalla.exceptions;

import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtExpiredTokenException extends AuthenticationException{

	private static final long serialVersionUID = 8197073116749171341L;

	public JwtExpiredTokenException(String msg, ExpiredJwtException expired) {
		super(msg,expired);
	}

	
	
}
