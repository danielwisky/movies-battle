package br.com.danielwisky.moviesbattle.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "basicAuth", scheme = "basic")
public class SpringDocConfiguration {

  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI().info(new Info().title("Movies Battle").version("1.0.0"));
  }
}