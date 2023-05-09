package kr.cafeIn.cafeorder.controller;

import javax.validation.Valid;
import kr.cafeIn.cafeorder.annotation.CurrentUserId;
import kr.cafeIn.cafeorder.annotation.LoginCheck;
import kr.cafeIn.cafeorder.annotation.WithdrawalCheck;
import kr.cafeIn.cafeorder.exception.WithdrawalException;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginForgotRequest;
import kr.cafeIn.cafeorder.model.dto.request.LoginRequest;
import kr.cafeIn.cafeorder.model.dto.request.PasswordResetRequest;
import kr.cafeIn.cafeorder.service.LoginService;
import kr.cafeIn.cafeorder.service.OtpService;
import kr.cafeIn.cafeorder.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  private final OtpService otpService;

  private final LoginService loginService;

  /**
   * 회원가입
   *
   * @return
   */

  @WithdrawalCheck
  @PostMapping("/signup")//form 데이터 보낼때 ( 데이터 등록시 사용) url은 똑같지만 !
  @ResponseStatus(HttpStatus.CREATED)
  public void signup(@Valid @RequestBody User user) {
    userService.join(user);


  }

  /**
   * 로그인
   *
   * @param loginReq 정보 입력
   */
  @PostMapping
  @WithdrawalCheck
  @ResponseStatus(HttpStatus.OK)
  public void login(@Valid @RequestBody LoginRequest loginReq) {

    loginService.login(loginReq);

  }

  /**
   * 로그아웃
   */
  @PostMapping("/logout")
  @LoginCheck
  @ResponseStatus(HttpStatus.OK)
  public void logout() {
    loginService.logout();

  }

  /**
   * 회원탈퇴
   */
  @LoginCheck
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PutMapping("/leave")
  public void withdraw(@CurrentUserId Long id) throws WithdrawalException {

    userService.withdraw(id);

  }

  /**
   * 비밀번호 변경
   */

  @PostMapping("/forgot")
  @ResponseStatus(HttpStatus.OK)
  public void forgotPassword(@Valid @RequestBody LoginForgotRequest loginForgotReq) {

    otpService.saveOtp(loginForgotReq);

  }

  @PutMapping("/forgot/change")
  @ResponseStatus(HttpStatus.OK)
  public void changePassword(@RequestBody PasswordResetRequest passwordResetReq) {
    otpService.updatePassword(passwordResetReq);
  }


}
