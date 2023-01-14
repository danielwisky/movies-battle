package br.com.danielwisky.moviesbattle.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("local")
public class CorsConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**");
  }
}