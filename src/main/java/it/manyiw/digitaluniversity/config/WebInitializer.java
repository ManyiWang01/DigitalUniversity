package it.manyiw.digitaluniversity.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 1. Where are your Service and Repository beans defined?
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { JavaConfig.class };
    }

    // 2. Where is your Web (Controller) configuration?
    // We can use the same class for now or a separate WebConfig
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    // 3. Which URLs should this Spring app handle?
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // Handle all requests starting from root
    }
}