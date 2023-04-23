package kr.cafeIn.cafeorder.aop;


import kr.cafeIn.cafeorder.exception.RequiredLoginException;
import kr.cafeIn.cafeorder.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * AOP 해당 어노테이션이 있다면 로그인을 체크해야 한다 (공통로직) 로그인 검증 AOP.
 */
//Aspect 역할을 할 클래스를 선언하기 위해 어노테이션 선언
@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

  private final LoginService loginService;

  /**
   * 카페 자리 예약시  로그인 체크 여부 확인 {@link kr.cafeIn.cafeorder.annotation.LoginCheck}가 붙어있는 메소드에 로그인 검증 AOP
   * 적용.
   */
  @Before("@annotation(kr.cafeIn.cafeorder.annotation.LoginCheck)")
  public Long loginCheck() {
    Long userId = loginService.getCurrentUser();
    if (userId == null) {
      throw new RequiredLoginException("이 서비스는 로그인이 필요합니다");
    }

    return userId;
  }
}