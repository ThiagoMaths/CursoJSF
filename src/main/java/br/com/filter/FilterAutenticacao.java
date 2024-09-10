package br.com.filter;

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/faces/*"})
public class FilterAutenticacao implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

        String url = req.getServletPath();

        if (!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsf");
            rd.forward(request, response);
            return;

        } else {
            chain.doFilter(request, response);
        }


    }

    public void init(FilterConfig filterConfig) throws ServletException {
        JPAUtil.getEntityManager();
    }

}
