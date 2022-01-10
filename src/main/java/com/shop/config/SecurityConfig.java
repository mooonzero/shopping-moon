package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http)throws Exception {
        //http 요청에 대한 보안 설정.

        //로그인, 로그아웃 구현
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

    }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
        //BCryptPasswordEncoder의 해시함수를 이용해 비밀번호를 암호화하여 저장.
    }

    //spring sequrity에서 인증을 위한 AuthenticationManagerBuilder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        //userDetailService를 구현하고 있는 객체로 memberService를 지정해주며,
        //pw 암호화를 위해 passwordEncoder를 지정
        auth.userDetailsService(memberService)
              .passwordEncoder(passwordEncoder());
    }


}
