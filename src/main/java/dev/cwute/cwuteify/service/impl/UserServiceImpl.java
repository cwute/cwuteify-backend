package dev.cwute.cwuteify.service.impl;

import dev.cwute.cwuteify.model.UserAccount;
import dev.cwute.cwuteify.repository.UserRepository;
import dev.cwute.cwuteify.service.InviteCodeService;
import dev.cwute.cwuteify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  @Autowired private final UserRepository userRepository;

  @Autowired private final InviteCodeService inviteCodeService;

  public void registerUser(UserAccount userAccount, String inviteCode) {
    userRepository.save(userAccount);
    inviteCodeService.useInviteCode(inviteCode);
  }

  public ResponseEntity<UserAccount> getUserByUsername(String username) {
    return ResponseEntity.of(userRepository.findByUsername(username));
  }

  public ResponseEntity<UserAccount> getUserByEmail(String email) {
    return ResponseEntity.of(userRepository.findByEmail(email));
  }

  public Boolean usernameExists(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public Boolean emailExists(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  @Override
  public ResponseEntity<List<UserAccount>> getUsers() {
    return ResponseEntity.of(Optional.of(userRepository.findAll()));
  }
}
