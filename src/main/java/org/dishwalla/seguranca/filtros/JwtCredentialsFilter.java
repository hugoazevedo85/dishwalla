package org.dishwalla.seguranca.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dishwalla.seguranca.configs.SecurityConstants;
import org.dishwalla.seguranca.tokens.JwtAuthenticationToken;
import org.dishwalla.seguranca.tokens.TokenService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JwtCredentialsFilter extends AbstractAuthenticationProcessingFilter{

	private final TokenService tokenService;
	
	public JwtCredentialsFilter(RequestMatcher requiresAuthenticationRequestMatcher, TokenService tokenService) {
		super(requiresAuthenticationRequestMatcher);
		this.tokenService = tokenService;
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		String header = request.getHeader(SecurityConstants.TOKEN_HEADER);
		String token = tokenService.extractToken(header);
		JwtAuthenticationToken auth = new JwtAuthenticationToken(token);
		return getAuthenticationManager().authenticate(auth);
		
	}

	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);
		chain.doFilter(request, response);
	}
	
}
