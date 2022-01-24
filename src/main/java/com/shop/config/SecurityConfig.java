package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

        http.authorizeRequests()
                .mvcMatchers("/","/members/**","/item/**","/images/**")
                .permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        //http.authorizeRequests() : sequrity처리에 HttpServletRequest를 이용하겠다.
        //permitAll() : 모든 사용자가 인증(로그인) 없이 해당 경로에 접근할 수 있도록 설정
        //mvcMatchers("/admin/**").hasRole("ADMIN") : /admin으로 시작하는 경로는 해당 계정이 ADMIN Role일 경우에만 접근 가능하도록 설정
        //anyRequest().authenticated() : 위에서 설정해준 경로를 제외한 나머지 경로들은 모드 인증을 요구하도록 설정

        /* 상위 식으로 현재 ADMIN 아이디로 로그인 한 경우가 아닌 이상 /admin/** 페이지에 접속할 때 에러 페이지 뜸
        *  나중에 상품 등록 페이지로 넘어가는 이동 버튼 만들 때, USER 아이디로 누르면 alert 창 뜨게 해주는걸로 만족해야하나 ...
        * */

        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        //인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록
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

    @Override
    public void configure(WebSecurity web) throws Exception{
        //static 디렉토리의 하위 파일은 인증을 무시하도록 설정
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }

}
