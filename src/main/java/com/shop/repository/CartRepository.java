package com.shop.repository;
//JpaRepository를 상속 받는 CartRepository 인터페이스 생성

import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart, Long> {

}
