package com.unibond.unibond.disease.domain;

import com.unibond.unibond.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Disease extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String diseaseNameKor;

    @Column(updatable = false)
    private String diseaseNameEng;
}
