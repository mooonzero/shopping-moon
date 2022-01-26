package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity{

    //상품 코드
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //상품명
    @Column(nullable = false, length = 50)
    private String itemNm;

    //가격
    @Column(name = "price", nullable = false)
    private int price;

    //재고수량
    @Column(nullable = false)
    private  int stockNumber;

    //상품 상세 설명
    @Lob
    // Lob : BLOB,CLOB 타입 매핑
    @Column(nullable = false)
    private String itemDetail;

    //상품 판매 상태
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    //BaseEntity를 상속 받아서 두 변수 삭제
    //등록 시간
    //private LocalDateTime regTime;
    //수정 시간
    //private LocalDateTime updateTime;

    //상품 데이터를 업데이트 하는 로직
    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }
}
