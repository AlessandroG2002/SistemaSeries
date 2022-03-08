package br.unitins.series.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.unitins.series.model.Usuario;

@WebFilter(filterName = "SecurityFilter",urlPatterns= {"/pages/*"})

public class SecurityFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String endereco = servletRequest.getRequestURI();
		System.out.println(endereco);
		
		HttpSession session = servletRequest.getSession(false);
		
		Usuario usuario = null;
		if (session != null)
			usuario = (Usuario) session.getAttribute("usuarioLogado");
		
		if (usuario == null) {
			((HttpServletResponse) response).sendRedirect("/Series/login.xhtml");
		} else {
			if (usuario.getPerfil().getPaginasComPermissao().contains(endereco)) {
				chain.doFilter(request, response);
				return;
			} else 
				((HttpServletResponse) response).sendRedirect("/Series/pages/sempermissao.xhtml");
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Security inicializado...");
		
		Filter.super.init(filterConfig);
	}
	
}
