package org.dishwalla.seguranca.tokens;

import java.util.Collection;

import org.dishwalla.seguranca.models.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 2771034576184983561L;
	
	private String token;
	private AuthUser authUser;
	
	public JwtAuthenticationToken(String token) {
		super(null);
		this.token = token;
	}
	
	public JwtAuthenticationToken(AuthUser authUser, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(true);
		this.authUser = authUser;
		this.token = null;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getPrincipal() {
		return authUser;
	}

	
	
}
