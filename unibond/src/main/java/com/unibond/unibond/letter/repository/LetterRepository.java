package com.unibond.unibond.letter.repository;

import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
            "left join LetterBlock lb on ( l  = lb.reportedLetter and lb.reporter.id = :participant ) " +
            "join fetch l.sender " +
            "join fetch l.sender.disease " +
            "join fetch l.receiver " +
            "join fetch l.receiver.disease " +
            "where l.letterRoom.id = :letterRoom " +
            "and ( (l.receiver.id = :participant and l.letterStatus = 'ARRIVED' ) or (l.sender.id = :participant) )" +
            "and l.status = 'ACTIVE' " +
            "and lb.id IS NULL " +
            "order by l.createdDate desc")
    Page<Letter> findLettersByLetterRoomAndReceiverOrSender(@Param("letterRoom") Long letterRoomId,
                                                            @Param("participant") Long participantId, // loginId
                                                            Pageable pageable);

    @Query("select case when count(l)> 0 then true else false end from Letter l " +
            "where (l.sender.id = :sender and l.receiver.id = :receiver) " +
            "and l.createdDate > :currentTimeMinusOneHour ")
    Boolean hasSentLetterToSamePersonWithinHour(@Param("sender") Long senderId,
                                                @Param("receiver") Long receiverId,
                                                @Param("currentTimeMinusOneHour") LocalDateTime currentTimeMinusOneHour);

    @Query("select l from Letter l " +
            "left join MemberBlock mb on ( l.receiver.id = :receiverId and mb.reporter.id = :receiverId and l.sender = mb.respondent ) " +
            "left join LetterBlock lb on ( l = lb.reportedLetter and lb.reporter.id = :receiverId ) " +
            "join fetch l.receiver " +
            "join fetch l.sender " +
            "where l.receiver.id = :receiverId and l.liked = true and l.letterStatus = 'ARRIVED' and l.status = 'ACTIVE' " +
            "and mb.id IS NULL and lb.id IS NULL ")
    Page<Letter> findLikedLetterByReceiver(@Param("receiverId") Long receiverId, // loginId
                                           Pageable pageable);

    @Modifying
    @Query("update Letter l set l.letterStatus = 'ARRIVED' " +
            "where l.createdDate <= :currentTimeMinusOneHour and l.letterStatus = 'SENDING'")
    void bulkSendLetter(@Param("currentTimeMinusOneHour") LocalDateTime currentTime);

    @Modifying
    @Query("update Letter l set l.status = 'DELETED' " +
            "where l.sender.id = :memberId or l.receiver.id = :memberId ")
    void bulkDeleteByMember(@Param("memberId") Long memberId);
}
