package pt.ua.deti.codespell.codespellbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CodeSpellBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSpellBackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://159.65.60.64:3000", "https://dev.codespell.live")
                        .allowedMethods("GET", "POST","PUT", "DELETE");
            }

        };
    }

}
