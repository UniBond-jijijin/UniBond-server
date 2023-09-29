package com.unibond.unibond.letter_room.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class LetterRoom {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
