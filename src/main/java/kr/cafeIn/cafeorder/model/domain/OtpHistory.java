package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OtpHistory {

  private Long id;
  private Long userId; //userId 참조
  private String otpKey; // opt
  private LocalDateTime otpExpireTime; //otp 생성 날짜


  public OtpHistory(Long userId, String otpKey, LocalDateTime otpExpireTime) {
    this.userId = userId;
    this.otpKey = otpKey;
    this.otpExpireTime = otpExpireTime;
  }
}
