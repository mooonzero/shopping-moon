package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태

    //mappedBy 속성의 값으로 연관 관계의 주인을 설정
    //CascadeType.ALL  : 주문 영속성 전이
    //orphanRemoval = true : 고아 객체 제거
    //fetch = FetchType.LAZY : 지연 로딩
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
                orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
    //하나의 주문이 여러 개의 주문 상품을 가지므로 List 자료형을 사용해서 매핑

    //BaseEntity를 상속 받아서 두 변수 삭제
    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;


}
