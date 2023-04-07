package kr.cafeIn.cafeorder.model.dto.request;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 로그인 입력 정보
 */
@Getter
@Setter
@NoArgsConstructor //파라 미터가 없는 기본생성자의 무분별한 생성을 막아서 의도하지 앟은 엔티티를 만드는것을 막다.
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 만듦
@Builder
public class LoginReq {

  @NotBlank(message = "이메일을 입력해주세요")///문자열 유무 검증 Null, "", 공백을 포함한 빈값은 안된다 체크
  @Email(message = "이메일 양식을 지켜주세요")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요")
  private String password;


}
