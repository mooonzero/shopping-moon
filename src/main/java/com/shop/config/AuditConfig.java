package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing : Jpa의 Auditing 기능을 활성화
@Configuration
@EnableJpaAuditing
public class AuditConfig {

    // 등록자와 수정자를 처리해주는 AditorAware를 빈으로 등록
    //개발자의 직접 제어가 불가능한 외부 라이브러리등을 Bean으로 만들때 사용하는 어노테이션
    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }
}
