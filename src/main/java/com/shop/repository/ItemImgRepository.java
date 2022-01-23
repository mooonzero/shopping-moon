package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface ItemImgRepository extends JpaRepositoryImplementation<ItemImg, Long> {

    //이미지가 저장이 제대로 이루어지는지 확인하기 위한 method
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
