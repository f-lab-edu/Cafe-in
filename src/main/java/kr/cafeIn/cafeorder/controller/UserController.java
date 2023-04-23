package kr.cafeIn.cafeorder.controller;

import javax.validation.Valid;
import kr.cafeIn.cafeorder.annotation.CurrentUserId;
import kr.cafeIn.cafeorder.annotation.LoginCheck;
import kr.cafeIn.cafeorder.annotation.WithdrawalCheck;
import kr.cafeIn.cafeorder.exception.WithdrawalException;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginForgotReq;
import kr.cafeIn.cafeorder.model.dto.request.LoginReq;
import kr.cafeIn.cafeorder.model.dto.request.PasswordResetReq;
import kr.cafeIn.cafeorder.service.LoginService;
import kr.cafeIn.cafeorder.service.OtpService;
import kr.cafeIn.cafeorder.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller.
 *
 * @RestController 는 @Controller 는 view 페이지를 반환하는데 ResetAPI 를 개발하는 상황에서는 @ResponseBody 를 붙여서 데이터를
 * 그대로 반환 하도록 할 수 있다. 이 두개를 합친것이 @ResetController 이다
 */

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

  @GetMapping("/users")//조회 할때 주로 사용
  public String createForm() {
    return "user";
  }

  @WithdrawalCheck
  @PostMapping("/singup")//form 데이터 보낼때 ( 데이터 등록시 사용) url은 똑같지만 !
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
  public void login(@Valid @RequestBody LoginReq loginReq) {

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
    log.info(" id: {}",  id);
    userService.withdraw(id);

  }

  /**
   * 비밀번호 변경
   */

  @PostMapping("/forgot")
  @ResponseStatus(HttpStatus.OK)
  public void forgotPassword(@Valid @RequestBody LoginForgotReq loginForgotReq) {
    log.info("hamham : {}",loginForgotReq);
    otpService.saveOtp(loginForgotReq);

  }

  @PutMapping("/forgot/change")
  @ResponseStatus(HttpStatus.OK)
  public void changePassword(@RequestBody PasswordResetReq passwordResetReq) {
    otpService.updatePassword(passwordResetReq);
  }


}
