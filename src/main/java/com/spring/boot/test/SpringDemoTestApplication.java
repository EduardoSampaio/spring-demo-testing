package com.spring.boot.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAdminServer
@EnableCaching
public class SpringDemoTestApplication implements CommandLineRunner  {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoTestApplication.class, args);
    }
    
	@Override
	public void run(String... args) throws Exception {
		
	}

}
