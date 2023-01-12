package dev.cwute.cwuteify.service.impl;

import dev.cwute.cwuteify.model.UserAccount;
import dev.cwute.cwuteify.service.JwtUserDetailsService;
import dev.cwute.cwuteify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService, JwtUserDetailsService {
  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccount userAccount = userService.getUserByUsername(username).getBody();
    assert userAccount != null;
    return new org.springframework.security.core.userdetails.User(
        userAccount.getUsername(),
        userAccount.getPassword(),
        userAccount.isEnabled(),
        userAccount.isAccountNonExpired(),
        userAccount.isCredentialsNonExpired(),
        userAccount.isAccountNonLocked(),
        userAccount.getAuthorities());
  }
}
