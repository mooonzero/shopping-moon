package com.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    //동적 query 생성을 위해 사용
    private JPAQueryFactory queryFactory;
    //JPAQueryFactory 생성자로 em 객체 넣어줌.
    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus ==
                null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        }else if(StringUtils.equals("1d",searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if(StringUtils.equals("1w",searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m",searchDateType)){
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m",searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%"+searchQuery+"%");
        }else if (StringUtils.equals("createBy", searchBy)){
            return QItem.item.createBy.like("%"+searchQuery+"%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){

//        QueryResults<Item> results = queryFactory.selectFrom(QItem.item)
//                                        .where(regDtsAfter(itemSearchDto.getSearchDateType()),
//                                                searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//                                                searchByLike(itemSearchDto.getSearchBy(),
//                                                        itemSearchDto.getSearchQuery()))
//                                        .orderBy(QItem.item.id.desc())
//                                        .offset(pageable.getOffset())
//                                        .limit(pageable.getPageSize())
//                                        .fetchResults();
        //where 조건 절 안의 ','는 and로 인식됨
        //offset : 데이터를 가지고 올 시작 인덱스를 지정
        //limit : 한 번에 가지고 올 최대 개수를 지정
        //fetchResults() : 조회한 리스트 및 전체 개수를 포함하는 QueryResults를 반환.
        //querydsl 5.0부터는 fetchResults()와 fetchCount()가 deprecated 됨.
        //QueryResults로 count query를 반환하는게 완벽하게 지원 X, total count를 사용할 필요가 없으면 fetch() 사용을 권장.

        List<Item> results = queryFactory.selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        // fetchResults 대신 fetch 사용을 위해 List로 반환 해주는걸로 바꿨지만
        // fetchResult의 count나 paging이 안되니까 .. 아래 두 코드가 오류 ... count 내는걸 size()로 수정해줬지만 권장하지 않는 방법..
        // 일단 이런식으로 ,, subList는 list에서 페이징 처리 해주는 방법 total은 size말고 다른 방법 찾아보기
        List<Item> content = results.subList(1,5);
        long total = results.size();
        return new PageImpl<>(content,pageable,total);
    }
}
