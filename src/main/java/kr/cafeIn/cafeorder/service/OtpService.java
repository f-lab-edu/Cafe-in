package kr.cafeIn.cafeorder.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.mail.MessagingException;
import kr.cafeIn.cafeorder.exception.EmailMessagingException;
import kr.cafeIn.cafeorder.exception.InvalidRequestException;
import kr.cafeIn.cafeorder.ext.EmailSender;
import kr.cafeIn.cafeorder.mapper.OtpHistoryMapper;
import kr.cafeIn.cafeorder.model.domain.OtpHistory;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginForgotReq;
import kr.cafeIn.cafeorder.model.dto.request.PasswordResetReq;
import kr.cafeIn.cafeorder.utils.PasswordEncrypt;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * OTP 생성 및 이메일 전송을 위한 코드
 */

@AllArgsConstructor
@Service
@Slf4j
public class OtpService {

  private final GoogleAuthenticator gAuth;

  private final OtpHistoryMapper otpHistoryMapper;
  private final UserService userService;
  private final EmailSender smtpSender;



  /**
   * OPT 발급
   *
   * @return
   */

  public String generateOtp() {
    // 새 opt 생성
    GoogleAuthenticatorKey key = gAuth.createCredentials();
    return key.getKey();

  }

  /**
   * SMTP 프로토콜을 사용하여 전자 메일을 보내기 위한 속성 설정
   *
   * @param email
   * @param otp
   */
  public void sendOtpByEmail(String email, String otp) throws MessagingException {
    try {
      smtpSender.send(email, otp);
    } catch (Exception e) {
      // TODO
      throw new RuntimeException(e);
    }
  }

  // user 가 존재하는 사용자 인지 확인 후에 opt 생성해서 해당 메일에 메세지를 보내준다.
  public void saveOtp(LoginForgotReq loginForgotReq) {
    log.info("loginForgotReq:{}",loginForgotReq);
    Optional<User> userOptional = userService.findOne(loginForgotReq.getEmail());
    log.info("userOptional{}",userOptional);
    User user = userOptional.orElseThrow(() -> {
      throw new IllegalArgumentException();
    });
    Long userid=user.getId();
    //opt 생성
    String otp = generateOtp();
    log.info("otp test : {}", otp);
    //현재 시간에서 5분뒤의 시간까지 opt 유효 가능
    LocalDateTime otpExpireTime = LocalDateTime.now().plusMinutes(5);

    //otp_history 테이블에 저장
    OtpHistory otpHistory = new OtpHistory(userid,otp,otpExpireTime);

    // opt랑  setOptExpireTime을 저장하는 repository구현 (나중에 따로 OTP Mapper 로 만들기 )
    otpHistoryMapper.saveOtp(otpHistory);
    try {
      sendOtpByEmail(user.getEmail(), otp);
    } catch (MessagingException e) {
      throw new EmailMessagingException("이메일 전송 중 예외가 발생했습니다");
    }


    log.info("HAM test : {}", userOptional);
    //생성된 opt 번호 이메일로 전송
  }

  /**
   * OtpHistory 에 해당 opt 가 존재하는지 확인
   *
   */
  //@Transactional(readOnly = false)
  public boolean isExistsOtpKey(String otpKey){
    return otpHistoryMapper.existOtpKey(otpKey);
  }
  /**
   * 해당하는 opt 가 유효한지 체크
   * 현재시간이 유효시간 이후인지 체크하여 , 만료될 경우 true 반환 유효하다면 false 를 반환한다.
   */
  public boolean isOtpExpired(String otpKey){
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime validUntil = otpHistoryMapper.getExpireTime(otpKey);

    return  now.isAfter(validUntil);
  }

  //@Transactional
  public void updatePassword(PasswordResetReq passwordResetReq) {
    Optional<User> user = userService.findOne(passwordResetReq.getEmail());
    User user1 = user.orElseThrow(IllegalArgumentException::new);
    if (user.isEmpty()) {
      throw new IllegalArgumentException(); // TODO
    }


    //1. OtpService 에다가  사용자가 입력한 opt 번호가 DB에 존재하는지 확인
    if (!isExistsOtpKey(passwordResetReq.getOtp())) {
      throw new InvalidRequestException("잘못된 OTP");
    }

    //2. 존재할 경우 OTP 가 유효한지 확인
    if( isOtpExpired(passwordResetReq.getOtp())){
      throw new InvalidRequestException("OTP 유효기간이 만료되었습니다.");
    }

    //3.유효한 OTP 처리 로직

    //3-1.패스워드 변경 로직

    Long userid = user1.getId();

    String newPassword = PasswordEncrypt.encrypt(passwordResetReq.getNewPassword());
    log.info("userid:{}",userid,"newPassword:{}",newPassword);
    userService.updatePassword(userid,newPassword);

  }


}

