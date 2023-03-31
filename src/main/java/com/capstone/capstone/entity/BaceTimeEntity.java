package com.capstone.capstone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)// Auditing 사용. LocalDateTime
@MappedSuperclass
@Getter
public class BaceTimeEntity { //DB 생성시 생성 시간 및 수정 시간 추가용도.
    @CreatedDate// Entity가 생성되어 저장될 때 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime createDate;
    @LastModifiedDate// Entity 값을 변경할 때 시간 자동 저장
    private LocalDateTime modifiedDate;
}
