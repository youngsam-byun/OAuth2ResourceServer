package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.*;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Properties;

@Configuration
public class WebApplicationInit implements WebApplicationInitializer {

    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String DISPATCHER_SERVLET_NAME = "dispatcherServlet";
    private static final String DISPATCHER_SERVLET_MAPPING = "/";
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";


    private static final String PROPERTY_APPLICATION_PROPERTIES = "/property/application.properties";
    private String pf;


    public void onStartup(ServletContext container) throws ServletException {

        try {
            Resource resource = new ClassPathResource(PROPERTY_APPLICATION_PROPERTIES);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            pf = props.getProperty("profile");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        System.setProperty(SPRING_PROFILES_ACTIVE, pf);

        // character encoding UTF-8
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(CHARACTER_ENCODING);
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = container.addFilter("characterEncoding",
                characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/");

        // web app rootkey setup
        container.setInitParameter("webAppRootKey", "webapp.ys");

        // Log4j configurer
        container.addListener(Log4jConfigListener.class);
        container.setInitParameter("log4jConfigLocation", "classpath:log4j/log4j.properties");


        // application context configuration
        try (AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext()) {
            rootContext.register(AppConfig.class);
            container.addListener(new ContextLoaderListener(rootContext));
        }

        // Manage the lifecycle of the root application context

        // Create the dispatcher servlet's Spring application context
        try (AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext()) {
            dispatcherServlet.register(MVCConfig.class);
            ServletRegistration.Dynamic dispatcher = container.addServlet(DISPATCHER_SERVLET_NAME,
                    new DispatcherServlet(dispatcherServlet));
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);
        }

    }

}
