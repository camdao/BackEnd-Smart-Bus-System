package com.SmartBus.domain.member.api;

import com.SmartBus.domain.member.application.MemberService;
import com.SmartBus.domain.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/drivers")
    public ResponseEntity<List<MemberResponse>> getAllDrivers() {
        return ResponseEntity.ok(memberService.getAllDrivers());
    }


    @GetMapping("/non-admins")
    public ResponseEntity<List<MemberResponse>> getAllNonAdmins() {
        return ResponseEntity.ok(memberService.getAllNonAdmins());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMembersById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMembersById(id));
    }
}