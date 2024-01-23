//
//package com.codebrew.roommart.filter;
//
//import java.io.IOException;
//import java.util.Properties;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//
//public class DispatcherFilter implements Filter {
//
//    public DispatcherFilter() {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//            FilterChain chain)
//            throws IOException, ServletException {
//
//
//        String url;
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String uri = httpRequest.getRequestURI();
//
//        int lastIndex = uri.lastIndexOf("/");
//        String resource = uri.substring(lastIndex + 1);
//        System.out.println(resource);
//
//        try {
//            ServletContext context = request.getServletContext();
//            Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
//
//            // lay url tu sitemap.properties
//            url = siteMap.getProperty(resource);
//            System.out.println(url);
//
//            if (url != null) {
//                RequestDispatcher rd = httpRequest.getRequestDispatcher(url);
//                rd.forward(request, response);
//            } else
//                chain.doFilter(request, response);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    public void init() {
//
//    }
//
//}
