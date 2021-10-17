package com.example.travelproject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path userUploadPhotoDir = Paths.get("./user-photos");
        String userUploadPhotoPath = userUploadPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/user-photos/**").addResourceLocations("file:" +userUploadPhotoPath +"/");
    }
}
