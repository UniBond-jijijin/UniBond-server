package com.unibond.unibond.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
