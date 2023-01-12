package dev.cwute.cwuteify.controller;

import dev.cwute.cwuteify.model.InviteCode;
import dev.cwute.cwuteify.service.InviteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminFunctionsController {
  @Autowired private InviteCodeService inviteCodeService;

  @PostMapping("/generate-invite-code")
  public ResponseEntity<String> generateInviteCode() {
    return inviteCodeService.generateInviteCode();
  }

  @GetMapping("/get-invite-codes")
  public ResponseEntity<List<InviteCode>> getInviteCodes() {
    return inviteCodeService.getInviteCodes();
  }

  @GetMapping("/get-invite-code/{code}")
  public ResponseEntity<InviteCode> getInviteCode(@PathVariable String code) {
    return inviteCodeService.getInviteCode(code);
  }
}
