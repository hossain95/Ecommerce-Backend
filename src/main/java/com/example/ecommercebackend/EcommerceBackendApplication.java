package com.example.ecommercebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EcommerceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:8080");
            }
        };
    }
//    @Bean
//    public OpenAPI openApiConfig(){
//        return new OpenAPI().info(apiInfo());
//    }
//    public Info apiInfo(){
//        Info info = new Info();
//        info
//                .title("Ecommerce api")
//                .version("v0.1");
//        return info;
//
//    }

}
