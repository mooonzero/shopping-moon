package com.shop.controller;


import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    //mockMvc 객체를 이용하면 웹 브라우저에서 요청하는 것처럼 테스트 가능
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    //로그인 전 회원을 등록하는 method
    public Member createMember(String email, String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("문영");
        memberFormDto.setAddress("서울 마포구");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto,passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("login test")
    public void loginSuccesssTest() throws Exception{
        String email = "test@test.com";
        String password = "12345678";
        this.createMember(email,password);
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }
}
