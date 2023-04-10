package dev.cwute.cwuteify.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cwute.cwuteify.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final int expirationTime;
  private final String secret;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final UserService userService;

  public AuthSuccessHandler(
      @Value("${jwt.expiration}") int expirationTime,
      @Value("${jwt.secret}") String secret,
      UserService userService) {
    this.expirationTime = expirationTime;
    this.secret = secret;
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String token =
        JWT.create()
            .withSubject(
                Objects.requireNonNull(
                        userService.getUserByUsername(userDetails.getUsername()).getBody())
                    .getUsername())
            .withExpiresAt(
                Instant.ofEpochMilli(
                    ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        + expirationTime))
                .withClaim("roles", userDetails.getAuthorities().toString()
                )
            .sign(Algorithm.HMAC256(secret));
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("Access-Control-Expose-Headers", "Authorization");
    //response.addHeader("Access-Control-Allow-Origin", "*");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write("{\"token\": \"" + token + "\"}");
  }
}
