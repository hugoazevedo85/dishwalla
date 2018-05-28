package org.dishwalla.seguranca.tokens;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.dishwalla.exceptions.JwtExpiredTokenException;
import org.dishwalla.seguranca.configs.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {
	
	public String createToken(String subject) {
		
        Date dataAExpirar = 
					Date.from(
						LocalDateTime.now()
						.plusMinutes(100)
						.atZone(ZoneId.systemDefault())
						.toInstant()
					);
        
        
		String token = 
				Jwts.builder()
					.setSubject(subject)
					.setExpiration(dataAExpirar)
					.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET_KEY)
					.compact();
		
		return token;
					
	}
	
	
	public Jws<Claims> getTokenClaims(String token){
		try {
			return Jwts.parser()
					.setSigningKey(SecurityConstants.TOKEN_SECRET_KEY)
					.parseClaimsJws(token);
		
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
			throw new BadCredentialsException("Token inválido",ex);
		}
		catch(ExpiredJwtException expired) {
			throw new JwtExpiredTokenException("Token Expirado", expired);
		}
	}
	
	
	public String extractToken(String headerString) {
		if (StringUtils.isEmpty(headerString)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (headerString.length() < SecurityConstants.TOKEN_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return headerString.substring(SecurityConstants.TOKEN_PREFIX.length(), headerString.length());
	}
	
	
}
