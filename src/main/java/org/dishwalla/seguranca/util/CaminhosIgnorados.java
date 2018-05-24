package org.dishwalla.seguranca.util;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CaminhosIgnorados implements RequestMatcher{

	private OrRequestMatcher ignorados;
    private RequestMatcher raiz;
	
	public CaminhosIgnorados(List<String> listaCaminhos, String caminhoRaiz) {
		List<RequestMatcher> listMatchers = listaCaminhos.stream().map(caminho -> new AntPathRequestMatcher(caminho)).collect(Collectors.toList());
		ignorados = new OrRequestMatcher(listMatchers);
		raiz = new AntPathRequestMatcher(caminhoRaiz);
	}
	
	
	@Override
	public boolean matches(HttpServletRequest request) {
		if(ignorados.matches(request)) {
			return false;
		}
		return raiz.matches(request);
	}

}
