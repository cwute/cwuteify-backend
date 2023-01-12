package dev.cwute.cwuteify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.MultipartConfigElement;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "dev.cwute.cwuteify")
public class CwuteifyApplication {
  public static void main(String[] args) {
    SpringApplication.run(CwuteifyApplication.class, args);
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setMaxFileSize(DataSize.parse("124MB"));
    factory.setMaxRequestSize(DataSize.parse("124MB"));
    return factory.createMultipartConfig();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
        Arrays.asList(
            "https://api.cwute.dev",
            "https://cwute.dev",
            "https://test.cwute.dev",
            "https://localhost:3000",
            "https://localhost:727"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
