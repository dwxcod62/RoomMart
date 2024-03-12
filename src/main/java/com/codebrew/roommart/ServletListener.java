package com.codebrew.roommart;

import com.codebrew.roommart.utils.PropertiesFile;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class ServletListener implements ServletContextListener, HttpSessionListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String siteMapPath = context.getInitParameter("SITEMAP_PROPERTIES");

        String authorization = context.getInitParameter("AUTHORIZATION_PROPERTIES_FILE_LOCATION");
        Properties siteMapPro = PropertiesFile.getProperties(context, siteMapPath);

        Properties authorizationProperties = PropertiesFile.getProperties(context, authorization);
        context.setAttribute("SITE_MAP", siteMapPro);

        context.setAttribute("AUTHORIZATION_lIST", authorizationProperties);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
