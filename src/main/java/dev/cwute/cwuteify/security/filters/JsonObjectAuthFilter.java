package dev.cwute.cwuteify.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cwute.cwuteify.security.user.Login;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class JsonObjectAuthFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    try {
      BufferedReader reader = request.getReader();
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      Login credentials = objectMapper.readValue(sb.toString(), Login.class);
      UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(
              credentials.getUsername(), credentials.getPassword());
      setDetails(request, token);
      return this.getAuthenticationManager().authenticate(token);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return null;
    }
  }
}
