package kr.cafeIn.cafeorder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 관리자 검증 어노테이션 {@link kr.cafeIn.cafeorder.aop.OwnerCheckAspect}를 통해 AOP 적용
 *
 * @since 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OwnerCheck {

}
