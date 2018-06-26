package com.dairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DairyApplication extends SpringBootServletInitializer{
    
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    	return application.sources(DairyApplication.class);
	    }
	 
	public static void main(String[] args) {
		SpringApplication.run(DairyApplication.class, args);
		
		
	}
	
}
