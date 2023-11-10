package com.unibond.unibond.letter_room.repository;

import com.unibond.unibond.letter.repository.LetterRepository;
import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LetterRoomRepository extends JpaRepository<LetterRoom, Long> {

    @Query("select l from LetterRoom l " +
            "where " +
            "((l.member1 = :member1 and l.member2 = :member2) or " +
            "(l.member1 = :member2 and l.member2 = :member1)) " +
            "and l.status = 'PUBLIC'")
    Optional<LetterRoom> findLetterRoomBy2Member(Member member1, Member member2);
}
