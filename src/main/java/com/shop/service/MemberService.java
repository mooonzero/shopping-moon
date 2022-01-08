package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

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

}
