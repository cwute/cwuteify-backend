package dev.cwute.cwuteify.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.cwute.cwuteify.service.JwtUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends BasicAuthenticationFilter {

  private static final String token_prefix = "Bearer ";
  private final JwtUserDetailsService jwtUserDetailsService;
  private final String secret;

  public JwtAuthFilter(
      AuthenticationManager authenticationManager,
      JwtUserDetailsService jwtUserDetailsService,
      String secret) {
    super(authenticationManager);
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.secret = secret;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
    if (authentication == null) {
      chain.doFilter(request, response);
      return;
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (token == null || !token.startsWith(token_prefix)) {
      return null;
    }

    String username =
        JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token.replace(token_prefix, ""))
            .getSubject();
    if (username == null) {
      return null;
    }
    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
