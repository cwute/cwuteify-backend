package dev.cwute.cwuteify.controller;

import dev.cwute.cwuteify.enums.Role;
import dev.cwute.cwuteify.model.UserAccount;
import dev.cwute.cwuteify.security.user.Signup;
import dev.cwute.cwuteify.service.InviteCodeService;
import dev.cwute.cwuteify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private UserService userService;
  @Autowired private InviteCodeService inviteCodeService;

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody Signup signup) {
    if (userService.usernameExists(signup.getUsername())) {
      return ResponseEntity.badRequest().body("Username already exists");
    }
    if (userService.emailExists(signup.getEmail())) {
      return ResponseEntity.badRequest().body("Email already exists");
    }
    if (!inviteCodeService.isValidInviteCode(signup.getInviteCode())) {
      return ResponseEntity.badRequest().body("Invalid invite code");
    }

    userService.registerUser(
        UserAccount.builder()
            .username(signup.getUsername())
            .email(signup.getEmail())
            .password(passwordEncoder.encode(signup.getPassword()))
            .enabled(true)
            .role(Set.of(Role.ROLE_USER))
            .build(),
        signup.getInviteCode());

    return ResponseEntity.ok().body("User registered");
  }

  @GetMapping("/get-users")
  public ResponseEntity<List<UserAccount>> getUsers() {
    return userService.getUsers();
  }
}
