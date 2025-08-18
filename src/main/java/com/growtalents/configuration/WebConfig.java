package com.growtalents.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // tất cả route
                .allowedOrigins("http://localhost:5173",
                        "https://exe-201-grow-talents-fe-l8cd.vercel.app",
                        "http://growtalentsbe-production.up.railway.app") // FE Vite chạy port này
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
