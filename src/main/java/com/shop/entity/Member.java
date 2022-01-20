package com.shop.entity;


import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;
    // enum은 사용할 때 기본적으로 순서가 저장됨.
    // 순서가 바뀔 경우 문제가 발생할 수 있으므로, EnumType.STRING으로 저장

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        // spring security 설정 class에 등록한 BCryptPasswordEncoder Bean을 파라미터로 넘겨서 비밀번호를 암호화 해줌.
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
