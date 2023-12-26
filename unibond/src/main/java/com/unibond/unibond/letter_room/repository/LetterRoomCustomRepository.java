package com.unibond.unibond.letter_room.repository;

import com.unibond.unibond.letter_room.domain.LetterRoom;
import com.unibond.unibond.letter_room.repository.repo_interface.LetterRoomPreviewRepoInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRoomCustomRepository extends JpaRepository<LetterRoom, Long> {

    @Query(value =
            "select member1.id as member1Id, " +
                    "member1.profile_image as member1ProfileImg, " +
                    "member1.nickname as member1Nickname, " +
                    "member2.id as member2Id, " +
                    "member2.profile_image as member2ProfileImg, " +
                    "member2.nickname as member2Nickname, " +
                    "MAX(letter.created_date) as recentLetterCreatedDate, " +
                    "letterRoom.id as letterRoomId " +
            "from letter_room as letterRoom " +
            "join letter on letterRoom.id = letter.letter_room_id " +
            "join member member1 on member1.id = letterRoom.member_id1 " +
            "join member member2 on member2.id = letterRoom.member_id2 " +
            "where ((member1.id = :member) or (member2.id = :member)) " +
                "and letterRoom.status = 'ACTIVE' " +
                "and letter.status = 'ARRIVED' " +
            "group by letterRoom.id, member1.id, member2.id " +
            "order by recentLetterCreatedDate desc ",
            nativeQuery = true)
    Page<LetterRoomPreviewRepoInterface> findLetterRoomsByMember(@Param("member") Long memberId,
                                                                 Pageable pageable);
}
