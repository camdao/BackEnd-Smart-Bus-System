package com.SmartBus.domain.chat.dao;

import com.SmartBus.domain.chat.domain.ChatRoom;
import com.SmartBus.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT c FROM ChatRoom c WHERE (c.member1 = :member1 AND c.member2 = :member2) OR (c.member1 = :member2 AND c.member2 = :member1)")
    Optional<ChatRoom> findByMembers(@Param("member1") Member member1, @Param("member2") Member member2);

    @Query("SELECT c FROM ChatRoom c WHERE c.member1 = :member OR c.member2 = :member")
    List<ChatRoom> findAllByMember(@Param("member") Member currentUser);
}