package com.codebrew.roommart.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;


public class DispatcherFilter implements Filter {

    private FilterConfig config;

    public DispatcherFilter() {
    }

    public void init(FilterConfig config)  throws ServletException {
        this.config = config;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String url;
        try{
            ServletContext context = config.getServletContext();
            Properties siteMap = (Properties) context.getAttribute("SITE_MAP");

            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex+1);

            url = siteMap.getProperty(resource);

            if (url != null) {
                RequestDispatcher rd = httpRequest.getRequestDispatcher(url);
                rd.forward(request, response);
            }else {
                chain.doFilter(request, response);
            }

        }catch (Exception t){
            t.printStackTrace();
        }

    }

    @Override
    public void destroy() {
    }


}