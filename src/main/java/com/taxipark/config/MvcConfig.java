package com.taxipark.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer
{
    @Value("${upload.path}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://"+"/Robota/Courseworks/4 year, 7 semestr/Coursework(almostFinal)/src/main/resources/static/img"+"/");
    }
}
