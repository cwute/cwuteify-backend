package dev.cwute.cwuteify.utils;

import dev.cwute.cwuteify.enums.Role;
import dev.cwute.cwuteify.model.UserAccount;
import dev.cwute.cwuteify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AdminAccountCreator implements CommandLineRunner {

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    if (args.length == 0) {
      return;
    }
    if (!args[0].equals("--create-admin")) {
      return;
    }
    if (userRepository.findByUsername("admin").isPresent()) {
      return;
    }

    System.out.println("Creating admin account... make sure not to forget the password!");

    UserAccount userAdmin = new UserAccount();
    userAdmin.setEmail("admin@admin.com");
    userAdmin.setEnabled(true);
    userAdmin.AddRole(Role.ROLE_ADMIN);
    userAdmin.AddRole(Role.ROLE_USER);
    userAdmin.setUsername("admin");

    boolean passwordMatch = false;
    while (!passwordMatch) {
      System.out.println("Please enter the password for the admin account: ");
      Scanner scanner = new Scanner(System.in);
      String password = scanner.nextLine();
      System.out.println("Please re-enter the password for the admin account: ");
      String password2 = scanner.nextLine();
      if (password.equals(password2)) {
        userAdmin.setPassword(passwordEncoder.encode(password));
        passwordMatch = true;
      } else {
        System.out.println("Passwords do not match. Please try again.");
      }
    }
    System.out.println("Admin account saved: " + userRepository.save(userAdmin).toString());
  }
}
