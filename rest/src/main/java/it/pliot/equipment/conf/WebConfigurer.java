package it.pliot.equipment.conf;

import it.pliot.equipment.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    GlobalConfig config;

    @Bean
    public PliotResourceHandler customResourceHttpRequestHandler() {
        return new PliotResourceHandler( config );
    }

    @Bean
    public HandlerMapping customResourceHandlerMapping(PliotResourceHandler handler) {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap("/websupport/**", handler));
        return mapping;
    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*").allowedOrigins("http://localhost:4200");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }


}
