package dev.cwute.cwuteify.repository;

import dev.cwute.cwuteify.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
  Optional<UserAccount> findByUsername(String username);

  Optional<UserAccount> findByEmail(String email);
}
