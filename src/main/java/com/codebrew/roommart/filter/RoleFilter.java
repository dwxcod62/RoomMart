
package com.codebrew.roommart.filter;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RoleFilter implements Filter {
    
   @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

       HttpServletRequest httpRequest = (HttpServletRequest) request;
       HttpServletResponse httpResponse = (HttpServletResponse) response;

       Cookie[] c = httpRequest.getCookies();
       String token = null;
       String url = null;


       String uri = httpRequest.getRequestURI();
       int lastIndex = uri.lastIndexOf("/");
       String resource = uri.substring(lastIndex+1);

       HttpSession session = httpRequest.getSession(true);

       Account acc = null;
       if ((c != null || session.getAttribute("USER") != null) && (resource.isEmpty() || "success".equals(resource) || "login".equals(resource) || "loginPage".equals(resource))) {
           for (Cookie cookie : c) {
               if (cookie.getName().equals("selector")) {
                   token = cookie.getValue();
               }
           }

           acc = (token != null) ? new AccountDao().getAccountByToken(token) : (Account) session.getAttribute("USER");
           if(acc != null) {
               int role = acc.getRole();
               url = "dashboard";
               httpResponse.sendRedirect(url);
           }
           else chain.doFilter(request, response);
       }
       else
           chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    public void init(FilterConfig config)  throws ServletException {

    }
    
}
