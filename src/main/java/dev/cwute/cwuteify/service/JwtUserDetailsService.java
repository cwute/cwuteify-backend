package dev.cwute.cwuteify.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUserDetailsService {
  UserDetails loadUserByUsername(String username);
}
