
package com.codebrew.roommart.filter;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RoleFilter implements Filter {
    
   @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        Account acc = null;
        String token = null;
        String url = null;

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(true);
        Cookie[] c = httpRequest.getCookies();

        // lay lien ket
        String uri = httpRequest.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String resource = uri.substring(lastIndex + 1);

        // Neu cookie, session co thi se chuyen sang trang nguoi do, khong thi quay ve
        // login

        if ((session.getAttribute("USER") != null || c != null) &&
                ("login".equals(resource) || "log".equals(resource) || resource.isEmpty())) {

            for (Cookie cookie : c) {
                if (cookie.getName().equals("selector")) {
                    token = cookie.getValue();
                }
            }

            acc = ( token != null ) ? new SystemDao().getAccountByToken(token) : (Account) session.getAttribute("USER");
            if (acc != null) {
                url = "dashboard";
                httpResponse.sendRedirect(url);
            } else
                chain.doFilter(request, response);
        } else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    public void init(FilterConfig config)  throws ServletException {

    }
    
}
