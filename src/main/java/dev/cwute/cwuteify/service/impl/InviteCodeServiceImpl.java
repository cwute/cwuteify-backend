package dev.cwute.cwuteify.service.impl;

import dev.cwute.cwuteify.model.InviteCode;
import dev.cwute.cwuteify.repository.InviteCodeRepository;
import dev.cwute.cwuteify.service.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InviteCodeServiceImpl implements InviteCodeService {
  @Autowired InviteCodeRepository inviteCodeRepository;

  @Override
  public ResponseEntity<String> generateInviteCode() {
    UUID uuid = UUID.randomUUID();

    InviteCode inviteCode = new InviteCode();
    inviteCode.setCode(uuid.toString());

    inviteCodeRepository.save(inviteCode);

    return ResponseEntity.ok(uuid.toString());
  }

  @Override
  public void useInviteCode(String code) {
    InviteCode inviteCode =
        inviteCodeRepository
            .findByCode(code)
            .orElseThrow(() -> new RuntimeException("Invalid invite code"));

    inviteCode.setUsed(true);
    inviteCodeRepository.save(inviteCode);
  }

  @Override
  public ResponseEntity<List<InviteCode>> getInviteCodes() {
    List<InviteCode> inviteCodes = inviteCodeRepository.findAll();
    return ResponseEntity.of(Optional.of(inviteCodes));
  }

  @Override
  public ResponseEntity<InviteCode> getInviteCode(String code) {
    return ResponseEntity.of(inviteCodeRepository.findByCode(code));
  }

  @Override
  public Boolean isValidInviteCode(String code) {
    return inviteCodeRepository.findByCode(code).isPresent()
        && !inviteCodeRepository.findByCode(code).get().isUsed();
  }
}
