package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    //@Transactional : 로직을 처리하다가 에러가 발생하면, 변경된 데이터를 로직 수행하기 이전 상태로 콜백 시켜줌

    private final MemberRepository memberRepository;
    //@RequiredArgsConstructor : final이나 @NonNull이 붙은 필드에 생성자를 생성해줌

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember!=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    //로그인, 로그아웃 구현
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Member member = memberRepository.findByEmail(email);

        if (member == null){
            throw new UsernameNotFoundException(email);
        }

        //UserDetail을 구현하고 있는 User 객체를 반환.
        //User 객체를 생성하기 위해서 생성자로 회원의 email,pw,role을 param으로 넘겨줌
       return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}
