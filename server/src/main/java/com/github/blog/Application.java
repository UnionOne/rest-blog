package com.github.blog;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.SpringApplication.run;

/**
 * Created by union on 04.05.16.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }
}