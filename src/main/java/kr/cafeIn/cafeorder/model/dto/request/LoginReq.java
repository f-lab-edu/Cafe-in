package kr.cafeIn.cafeorder.model.dto.request;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



/*
 로그인 입력 정보

 */







@Getter
@Setter
@NoArgsConstructor //파라 미터가 없는 기본생성자의 무분별한 생성을 막아서 의도하지 앟은 엔티티를 만드는것을 막다.
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 만듦
@Builder
public class LoginReq {
    @NotBlank
    @Email
    private  String email;

    @NotBlank
    private  String password;



}
