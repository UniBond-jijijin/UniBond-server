package com.unibond.unibond.letter.repository;

import com.unibond.unibond.letter.domain.Letter;
import com.unibond.unibond.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("select l from Letter l " +
            "where l.receiver = :receiver " +
            "and l.id = :letterId " +
            "and l.status = 'ACTIVE'")
    Optional<Letter> findByReceiverAndLetterId(@Param("receiver") Member receiver,
                                         @Param("letterId") Long id);
}
