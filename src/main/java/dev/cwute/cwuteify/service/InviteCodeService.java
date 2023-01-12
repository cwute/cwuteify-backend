package dev.cwute.cwuteify.service;

import dev.cwute.cwuteify.model.InviteCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InviteCodeService {

  ResponseEntity<String> generateInviteCode();

  void useInviteCode(String code);

  ResponseEntity<List<InviteCode>> getInviteCodes();

  ResponseEntity<InviteCode> getInviteCode(String code);

  Boolean isValidInviteCode(String code);
}
