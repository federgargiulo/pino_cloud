package it.pliot.equipment.conf;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.web.EdgeSSOHandler;
import it.pliot.equipment.web.PliotResourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Collections;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    GlobalConfig config;

    @Autowired
    EdgeServices edgeServices;

    @Bean
    public PliotResourceHandler customResourceHttpRequestHandler() {
        return new PliotResourceHandler( config , edgeServices );
    }

    @Bean
    public HandlerMapping customResourceHandlerMapping(PliotResourceHandler handler) {

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap("/websupport/**", handler));
        return mapping;
    }
    @Bean
    @Profile( "!edge")
    public HandlerMapping ssoHandler( ) {
        EdgeSSOHandler handler = new EdgeSSOHandler( edgeServices );
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap( WebConf.SSO_PATH , handler));
        return mapping;
    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*").allowedOrigins("http://localhost:4200");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }


}
