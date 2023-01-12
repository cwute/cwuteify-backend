package dev.cwute.cwuteify.security.config;

import dev.cwute.cwuteify.security.AuthSuccessHandler;
import dev.cwute.cwuteify.security.filters.JsonObjectAuthFilter;
import dev.cwute.cwuteify.security.filters.JwtAuthFilter;
import dev.cwute.cwuteify.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class JwtSecurity {

  @Autowired private AuthenticationManager authenticationManager;
  private final AuthSuccessHandler authSuccessHandler;
  private final JwtUserDetailsService jwtUserDetailsService;
  private final String secret;

  public JwtSecurity(
      AuthSuccessHandler authSuccessHandler,
      JwtUserDetailsService jwtUserDetailsService,
      @Value("${jwt.secret}") String secret) {
    this.authSuccessHandler = authSuccessHandler;
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.secret = secret;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeHttpRequests(
            (auth) -> {
              try {
                auth.antMatchers("/song/search/{query}")
                    .hasRole("USER")
                    .antMatchers("/song/add")
                    .hasRole("ADMIN")
                    .antMatchers("/song/**/delete")
                    .hasRole("ADMIN")
                    .antMatchers("/song/**/get-track")
                    .hasRole("USER")
                    .antMatchers("/song/**/get-image")
                    .hasRole("USER")
                    .antMatchers("/song/all")
                    .hasRole("USER")
                    .antMatchers("song/**/get")
                    .hasRole("ADMIN")
                    .antMatchers("/playlist/**")
                    .hasRole("USER")
                    .antMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .antMatchers("/user/get-users")
                    .hasRole("ADMIN")
                    .antMatchers("/login")
                    .permitAll()
                    .antMatchers("/user/signup")
                    .permitAll()
                    .anyRequest()
                    .permitAll()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(authenticationFilter())
                    .addFilter(
                        new JwtAuthFilter(authenticationManager, jwtUserDetailsService, secret))
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            })
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public JsonObjectAuthFilter authenticationFilter() throws Exception {
    JsonObjectAuthFilter filter = new JsonObjectAuthFilter();
    filter.setAuthenticationSuccessHandler(authSuccessHandler);
    filter.setAuthenticationManager(authenticationManager);
    return filter;
  }
}
