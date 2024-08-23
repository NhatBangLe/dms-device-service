package ctu.cit.se.device_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIDocsConfig {

    @Bean
    public OpenAPI configOpenAPI() {
        var info = new Info()
                .title("Device Service")
                .version("1.0.0");

        var openAPI = new OpenAPI();
        openAPI.setInfo(info);
        return openAPI;
    }

}
