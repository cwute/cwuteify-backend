package dev.cwute.cwuteify.repository;

import dev.cwute.cwuteify.model.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {
  Optional<InviteCode> findByCode(String code);
}
