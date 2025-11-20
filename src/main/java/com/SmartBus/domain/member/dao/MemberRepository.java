package com.SmartBus.domain.member.dao;

import com.SmartBus.domain.member.domain.Member;
import com.SmartBus.domain.member.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    List<Member> findByRole(MemberRole role);

    List<Member> findByRoleNot(MemberRole role);
}
