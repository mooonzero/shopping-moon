package com.shop.config;
// Auditing 기능 활용하기

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        //Authentication : 인증에 필요한 권한, 유효한 이용자인지 등 모든 필드가 정해져 있음.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication !=null){
            //현재 로그인 한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 지정
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
