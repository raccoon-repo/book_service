package edu.library.springmvc;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class Initializer extends AbstractDispatcherServletInitializer {
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation("/WEB-INF/spring/servlet-ctx.xml");

        return ctx;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};  
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation("/WEB-INF/spring/root-ctx.xml");

        return ctx;
    }
}

