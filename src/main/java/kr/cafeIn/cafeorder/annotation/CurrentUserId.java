package kr.cafeIn.cafeorder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인시 UserId를 받아올 어노테이션. 해당 어노테이션이 있다면 , 리플랙션을 통해 true 인 값에 다가 resolver 에 등록한
 * loginService.getCurrentUser 를 실행 할수 있게 한다
 * {@link kr.cafeIn.cafeorder.utils.CurrentUserIdResolver}를 통해 파라미터로 받아서 사용할 수 있음.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CurrentUserId {

}