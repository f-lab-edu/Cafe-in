package kr.cafeIn.cafeorder.model.dto.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 정보 분실 email,nickname 입력
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginForgotRequest {

  @NotBlank(message = "이메일을 입력해주세요")
  @Email(message = "이메일 양식을 지켜주세요")
  private String email;

  @NotBlank(message = "닉네임을 입력해주세요")
  private String nickname;


}
