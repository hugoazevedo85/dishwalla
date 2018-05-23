package org.dishwalla.seguranca.tokens;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.dishwalla.seguranca.configs.SecurityConstants;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		return Jwts.parser()
				.setSigningKey(SecurityConstants.TOKEN_SECRET_KEY)
				.parseClaimsJws(token);
	}
	
	
}
