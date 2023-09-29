package com.unibond.unibond.alarm.domain;

import com.unibond.unibond.comment.domain.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class CommentAlarm extends Alarm {

    @ManyToOne
    private Comment comment;
}
