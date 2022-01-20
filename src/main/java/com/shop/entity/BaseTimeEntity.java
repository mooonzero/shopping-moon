package com.shop.entity;
//등록자, 수정자를 넣지 않은 테이블에 BaseTimeEntity만 상속 받을 수 있도록 생성한 클래스

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//Auditing을 적용하기 위해 @EntityListner 추가
//MappedSuperclass 공통 매핑 정보가 필요할 때 사용하는 어노테이션, 부모클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    //entity가 생성되어 저장될 때 시간을 자동으로 저장
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    //entity의 값을 변경할 때 시간을 자동으로 저장장
    @LastModifiedDate
    private  LocalDateTime updateTime;
}
