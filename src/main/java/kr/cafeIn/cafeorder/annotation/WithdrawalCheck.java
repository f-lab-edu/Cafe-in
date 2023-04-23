package kr.cafeIn.cafeorder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kr.cafeIn.cafeorder.aop.WithdrawalCheckAspect;

/**
 * 회원가입시 탈퇴 후 3일이 지났는지 검증 어노테이션 {@link WithdrawalCheckAspect}를 통해 AOP 적용
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithdrawalCheck {

}