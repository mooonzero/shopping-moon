package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberFormDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 4, max = 8, message = "비밀번호는 4자 이상, 8자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    //책에서는 name 빼고 나머지 어노테이션 NotEmpty로 해줬지만, 나머지도 빈 문자열 검사해줘야햘거 같아서 모두 NotBlank로 설정
}
