package com.unibond.unibond.letter.repository;

import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("select l from Letter l " +
            "where l.receiver = :receiver " +
            "and l.id = :letterId " +
            "and l.status = 'ACTIVE'")
    Optional<Letter> findByReceiverAndLetterId(@Param("receiver") Member receiver,
                                               @Param("letterId") Long id);

    @Query("select l from Letter l " +
            "join fetch l.sender " +
            "join fetch l.sender.disease " +
            "join fetch l.receiver " +
            "join fetch l.receiver.disease " +
            "where l.letterRoom.id = :letterRoom " +
            "and ( (l.receiver.id = :participant and l.letterStatus = 'ARRIVED' ) " +
            "or " +
            "(l.sender.id = :participant))" +
            "and l.status = 'ACTIVE'")
    List<Letter> findLettersByLetterRoomAndReceiverOrSender(@Param("letterRoom") Long letterRoomId,
                                                                        @Param("participant") Long participantId);
}
