package com.codebrew.roommart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;

public class PropertiesFile {
    public static Properties getProperties(ServletContext context, String fileRelativePath) {
        Properties prop = new Properties();

        try (InputStream input = context.getResourceAsStream(fileRelativePath)) {
            if (input != null) {
                prop.load(input);
            } else {

            }
        } catch (IOException ex) {
        }
        return prop;
    }
}
