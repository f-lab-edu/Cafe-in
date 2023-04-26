package kr.cafeIn.cafeorder.service;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey.Builder;
import java.time.LocalDateTime;
import java.util.Optional;
import kr.cafeIn.cafeorder.exception.InvalidOtpException;
import kr.cafeIn.cafeorder.ext.EmailSender;
import kr.cafeIn.cafeorder.mapper.OtpHistoryMapper;
import kr.cafeIn.cafeorder.model.domain.OtpHistory;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginForgotReq;
import kr.cafeIn.cafeorder.model.dto.request.PasswordResetReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class OtpServiceTest {

  private OtpService otpService;

  @Mock
  UserService userService;

  @Mock
  private OtpHistoryMapper otpHistoryMapper;

  @Mock
  private GoogleAuthenticator googleAuthenticator;

  @Mock
  private EmailSender emailSender;


  @BeforeEach
  public void beforeEach() {
    //OtpService Mockito 객체 생성

    MockitoAnnotations.openMocks(this);

    otpService = new OtpService(googleAuthenticator, otpHistoryMapper, userService, emailSender);


  }


  @Test
  @DisplayName("OTP 생성 테스트")
  void generateOtpTest() {
    //given
    when(googleAuthenticator.createCredentials()).thenReturn(new Builder("123456").build());
    //when
    String otp = otpService.generateOtp();

    //then
    Assertions.assertEquals("123456", otp);


  }

  @Test
  @DisplayName("OTP 전송 테스트")
  void sendOtpByEmailTest() throws Exception {
    //given
    Mockito.doNothing().when(emailSender).send(anyString(), anyString());

    //when
    otpService.sendOtpByEmail("test@test.com", "123456");

    //then
    verify(emailSender, times(1)).send("test@test.com", "123456");

  }

  @Test
  @DisplayName("OTP 저장 및 전송 테스트")
  void saveOtpTest() throws Exception {
    //given
    LoginForgotReq loginForgotReq = new LoginForgotReq();
    loginForgotReq.setEmail("test@test.com");
    User user = new User();
    user.setId(1L);
    user.setEmail("test@test.com");
    when(userService.findOne(anyString())).thenReturn(Optional.of(user));
    when(googleAuthenticator.createCredentials()).thenReturn(new Builder("123456").build());

    //when
    otpService.saveOtp(loginForgotReq);

    //then
    verify(emailSender, times(1)).send("test@test.com", "123456");
    verify(otpHistoryMapper, times(1)).saveOtp(Mockito.any(OtpHistory.class));
  }

  @Test
  @DisplayName("OTP 키 존재 여부 확인 테스트")
  void isExistsOtpKeyTest() {

    OtpHistory otpHistory = new OtpHistory(1L, "123456", LocalDateTime.now().plusMinutes(5));
    otpHistoryMapper.saveOtp(otpHistory);
    when(otpHistoryMapper.existOtpKey(otpHistory.getOtpKey())).thenReturn(true);

    // when
    boolean existsOtpKey = otpService.isExistsOtpKey(otpHistory.getOtpKey());

    // then
    Assertions.assertTrue(existsOtpKey);
  }

  @Test
  @DisplayName("OPT 키 유효성 테스트: 유효한 OTP")
  public void testIsOtpExpired() {
    //given
    OtpHistory otpHistory = new OtpHistory(1L, "123456", LocalDateTime.now().plusMinutes(5));
    otpHistoryMapper.saveOtp(otpHistory);

    when(otpHistoryMapper.getExpireTime(otpHistory.getOtpKey())).thenReturn(
        otpHistory.getOtpExpireTime());

    //when
    boolean isExpired = otpService.isOtpExpired(otpHistory.getOtpKey());

    //then
    assertFalse(isExpired);
  }


  @Test
  @DisplayName("OPT 키 유효성 테스트: 유효기간 만료")
  public void testIsOtpExpiredFalse() {

    //given
    OtpHistory otpHistory = new OtpHistory(1L, "123456", LocalDateTime.now());
    otpHistoryMapper.saveOtp(otpHistory);

    when(otpHistoryMapper.getExpireTime(otpHistory.getOtpKey())).thenReturn(
        otpHistory.getOtpExpireTime());

    //when
    boolean isExpired = otpService.isOtpExpired(otpHistory.getOtpKey());

    //then
    assert (isExpired == true);


  }

  @Test
  @DisplayName("패스워드 변경 시: 이메일을 찾을수 없을 경우")
  public void updatePasswordEmailNotFound() {
    // given
    String email = "notfound@example.com";
    PasswordResetReq passwordResetReq = new PasswordResetReq(email, "oldpassword", "123456",
        "123456");

    when(userService.findOne(anyString())).thenReturn(Optional.empty());

    // when and then
    assertThrows(IllegalArgumentException.class, () -> {
      otpService.updatePassword(passwordResetReq);
    });
  }

  @Test
  @DisplayName("패스워드 변경 시: 잘못된 OTP 일 경우 ")
  public void updatePasswordInvalidOtp() {
    // given
    String email = "user@example.com";
    PasswordResetReq passwordResetReq = new PasswordResetReq(email, "oldpassword", "123456",
        "123456");

    User user = new User();
    user.setId(1L);
    user.setEmail(email);
    when(userService.findOne(anyString())).thenReturn(Optional.of(user));

    when(otpService.isExistsOtpKey(passwordResetReq.getOtp())).thenReturn(false);

    // when and then
    assertThrows(InvalidOtpException.class, () -> {
      otpService.updatePassword(passwordResetReq);
    });

  }

  @Test
  @DisplayName("패스워드 변경 시: OTP 유효기간 만료")
  public void updatePasswordExpiredOtp() {
    // given
    String email = "user@example.com";
    PasswordResetReq passwordResetReq = new PasswordResetReq(email, "oldpassword", "123456",
        "123456");

    //user생성
    User user = new User();
    user.setId(1L);
    user.setEmail(email);
    when(userService.findOne(anyString())).thenReturn(Optional.of(user));

    //otp 테이블 생성
    OtpHistory otpHistory = new OtpHistory(1L, "oldpassword", LocalDateTime.now());
    otpHistoryMapper.saveOtp(otpHistory);

    when(otpHistoryMapper.getExpireTime(passwordResetReq.getOtp())).thenReturn(
        otpHistory.getOtpExpireTime());

    boolean isExpired = otpService.isOtpExpired(passwordResetReq.getOtp());
    assert (isExpired == true);
    when(otpService.isExistsOtpKey(passwordResetReq.getOtp())).thenReturn(true);

    // when and then
    assertThrows(InvalidOtpException.class, () -> {
      otpService.updatePassword(passwordResetReq);
    });

  }

  @Test
  @DisplayName("패스워드 변경 시: 유효한 OTP 비밀번호 변경 ")
  public void updatePasswordValidOtp() {
    // given
    String email = "user@example.com";
    PasswordResetReq passwordResetReq = new PasswordResetReq(email, "oldpassword", "123456",
        "123456");

    //user생성
    User user = new User();
    user.setId(1L);
    user.setEmail(email);
    when(userService.findOne(anyString())).thenReturn(Optional.of(user));

    //otp 테이블 생성
    OtpHistory otpHistory = new OtpHistory(1L, "oldpassword", LocalDateTime.now().plusMinutes(5));
    otpHistoryMapper.saveOtp(otpHistory);

    when(otpHistoryMapper.getExpireTime(passwordResetReq.getOtp())).thenReturn(
        otpHistory.getOtpExpireTime());

    boolean isExpired = otpService.isOtpExpired(passwordResetReq.getOtp());
    assertFalse(isExpired);
    when(otpService.isExistsOtpKey(passwordResetReq.getOtp())).thenReturn(true);

    // when
    otpService.updatePassword(passwordResetReq);

    // then
    // userService.updatePassword 메소드가 정상적으로 호출되었는지 검증
    verify(userService, times(1)).updatePassword(eq(user.getId()), anyString());
  }


}

