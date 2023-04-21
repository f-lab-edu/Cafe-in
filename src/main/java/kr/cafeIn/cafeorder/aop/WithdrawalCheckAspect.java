package kr.cafeIn.cafeorder.aop;


import java.util.Optional;
import kr.cafeIn.cafeorder.annotation.WithdrawalCheck;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.repository.UserRepository;
import kr.cafeIn.cafeorder.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class WithdrawalCheckAspect {


  private final UserRepository userRepository;

  private final UserService userService;

  /**
   * 회원가입시 탈퇴 후 재가입 회원인지 확인 후 재가입 회원일 경우 register 메소드를 호출 한다. register 호출 하여 탈퇴후 3일이 지난 회원인 경우는
   * withdrawAt 값을 null 로  업데이트 진행 {@link WithdrawalCheck}가 붙어있는 메소드에 회원가입 검증 AOP 적용.
   */


  @Before("@annotation(kr.cafeIn.cafeorder.annotation.WithdrawalCheck)")
  public void checkWithdrawal(JoinPoint joinPoint) throws Throwable {

    Object[] args = joinPoint.getArgs();

    // Get the email from the first argument
    String email = ((User) args[0]).getEmail();

    // Check if the user has withdrawn
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent() && userOptional.get().getWithdrawnAt() != null) {

      userService.register(userOptional);
    }
  }


}
