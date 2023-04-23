package kr.cafeIn.cafeorder.aop;


import java.util.Optional;
import kr.cafeIn.cafeorder.annotation.WithdrawalCheck;
import kr.cafeIn.cafeorder.exception.WithdrawalException;
import kr.cafeIn.cafeorder.mapper.UserMapper;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginReq;
import kr.cafeIn.cafeorder.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
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
@Slf4j
public class WithdrawalCheckAspect {


  private final UserMapper userMapper;

  private final UserService userService;


  /**
   * 회원가입시 탈퇴 후 재가입 회원인지 확인 후 재가입 회원일 경우 register 메소드를 호출 한다. register 호출 하여 탈퇴후 3일이 지난 회원인 경우는
   * withdrawAt 값을 null 로  업데이트 진행 {@link WithdrawalCheck}가 붙어있는 메소드에 회원가입 검증 AOP 적용.
   */
  @Before("@annotation(kr.cafeIn.cafeorder.annotation.WithdrawalCheck)")
  public void checkWithdrawal(JoinPoint joinPoint) throws Throwable {

    Object[] args = joinPoint.getArgs();

    // Get the email from the first argument
    String email;
    boolean isLoginReq = false;
    if (args[0] instanceof LoginReq) {
      email = ((LoginReq) args[0]).getEmail();
      isLoginReq = true;
    } else if (args[0] instanceof User) {
      email = ((User) args[0]).getEmail();
    } else {
      throw new IllegalArgumentException(
          "Unsupported argument type: " + args[0].getClass().getName());
    }

    // Check if the user has withdrawn
    log.info("email:{}", email);
    Optional<User> userOptional = userMapper.findByEmail(email);
    log.info(" userOptional: {}", userOptional);
    if (userOptional.isPresent() && userOptional.get().getWithdrawnAt() != null) {

      if (isLoginReq) {
        throw new WithdrawalException("이미 탈퇴한 회원입니다");
      } else {
        userService.register(userOptional);
      }

    }
  }


}
