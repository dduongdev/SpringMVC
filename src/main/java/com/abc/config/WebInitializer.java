package com.abc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

@Configuration
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {ApplicationConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebMvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	    super.onStartup(servletContext);
	    servletContext.setSessionTimeout(5000000); 
	    servletContext.getSessionCookieConfig().setMaxAge(300000); 
	}
	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
	    String tempDir = System.getProperty("java.io.tmpdir");
	    MultipartConfigElement multipartConfigElement =
	            new MultipartConfigElement(tempDir, 2097152, 4194304, 524288);
	    registration.setMultipartConfig(multipartConfigElement);
	}
	
	@Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
