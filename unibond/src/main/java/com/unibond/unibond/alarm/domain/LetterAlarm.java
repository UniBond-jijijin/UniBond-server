package com.unibond.unibond.alarm.domain;

import com.unibond.unibond.letter.domain.Letter;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class LetterAlarm extends Alarm {

    @ManyToOne
    private Letter letter;

}
