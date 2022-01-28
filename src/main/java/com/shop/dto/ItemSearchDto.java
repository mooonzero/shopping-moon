package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private Long searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    //조회할 검색어를 저장할 변수
    private String searchQuery = "";
}
