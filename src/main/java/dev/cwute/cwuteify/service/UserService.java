package dev.cwute.cwuteify.service;

import dev.cwute.cwuteify.model.UserAccount;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
  void registerUser(UserAccount userAccount, String inviteCode);

  ResponseEntity<UserAccount> getUserByUsername(String username);

  ResponseEntity<UserAccount> getUserByEmail(String email);

  Boolean usernameExists(String username);

  Boolean emailExists(String email);

  ResponseEntity<List<UserAccount>> getUsers();
}
