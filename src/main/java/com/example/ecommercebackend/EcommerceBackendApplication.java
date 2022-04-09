package com.example.ecommercebackend;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendApplication.class, args);
    }

    @Bean
    public OpenAPI openApiConfig(){
        return new OpenAPI().info(apiInfo());
    }
    public Info apiInfo(){
        Info info = new Info();
        info
                .title("Ecommerce api")
                .version("v0.1");
        return info;

    }
}
