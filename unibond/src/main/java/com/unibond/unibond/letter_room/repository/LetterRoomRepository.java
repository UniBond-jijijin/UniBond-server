package com.unibond.unibond.letter_room.repository;

import com.unibond.unibond.letter_room.domain.LetterRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRoomRepository extends JpaRepository<LetterRoom, Long> {
}
