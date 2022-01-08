package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>, QuerydslPredicateExecutor<Item> {
//  JpaRepository<class,key>
    // 첫번째에는 엔티티 타입 클래스, 두번째는 기본키 타입
    // 기본적인 CRUD 및 페이징 처리를 위한 메소드가 정의되어 있음.

    // find 문법 익히기  : find + (entity이름) + By + 변수
    List<Item> findByItemNm(String itemNm);

    //or 조건 처리하기
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //LessThan 조건 처리하기
    List<Item> findByPriceLessThan(Integer price);

    //OrderBy로 정렬 처리하기
    //내림차순으로 처리해주기 위해 OrderBy + 속성명 + Desc
    //오름차순은 Desc 대신 Asc
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    //@Query를 이용한 검색 처리하기     
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
}
