package dev.cwute.cwuteify.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signup {
  private String username;
  private String email;
  private String password;
  private String inviteCode;
}
