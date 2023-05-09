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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetRequest {

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
