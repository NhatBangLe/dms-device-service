package ctu.cit.se.device_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${ALLOWED_ORIGIN}")
    private String allowedOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowCredentials = !allowedOrigin.equals("*");

        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowCredentials(allowCredentials)
                .maxAge(3600);
    }

}
