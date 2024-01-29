package com.codebrew.roommart.filter;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;


@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException, IOException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String uri = httpRequest.getRequestURI();
//        try {
//            int lastIndex = uri.lastIndexOf("/");
//            String resource = uri.substring(lastIndex + 1);
//
//            ServletContext context = request.getServletContext();
//            Properties authProperties = (Properties) context.getAttribute("AUTHENTICATION_lIST");
//
//            HttpSession session = httpRequest.getSession(true);
//            Cookie[] c = httpRequest.getCookies();
//            String token = null;
//
//            String authen = (String) authProperties.getProperty(resource);
//            if (authen != null && authen.equals("denied") && session.getAttribute("USER") == null) {
//                Account acc = null;
//                if (c != null) {
//                    for (Cookie cookie : c) {
//                        if (cookie.getName().equals("selector")) {
//                            token = cookie.getValue();
//                        }
//                        if (token != null) acc = new AccountDao().getAccountByToken(token);
//                    }
//                }
//
//                if (acc == null) {
//                    ((HttpServletResponse) response).sendRedirect("login");
//                } else {
//                    session.setAttribute("CURRENT_PAGE", "dashboard");
//                    session.setAttribute("USER", acc);
//                    chain.doFilter(request, response);
//                }
//            } else {
//                chain.doFilter(request, response);
//            }
//        } catch (Exception e) {
//            System.out.println("Error in Authentication Filter : " + e);
//        }

        chain.doFilter(request, response);
    }
}