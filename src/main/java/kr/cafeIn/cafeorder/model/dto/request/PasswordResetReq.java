package kr.cafeIn.cafeorder.model.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor //파라 미터가 없는 기본생성자의 무분별한 생성을 막아서 의도하지 앟은 엔티티를 만드는것을 막다.
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 만듦
@Builder
public class PasswordResetReq {

  @NotBlank(message = "이메일을 입력해주세요")
  @Email
  private String email;

  @NotBlank(message = "이메일에 전송된 opt 를 입력해주세요")
  private String otp;

  @NotBlank(message = "새로운 패스워드를 입력하세요")
  @Size
  private String newPassword;

  @NotBlank(message = "패스워드를 확인해주세요")
  private String confirmPassword;
}
