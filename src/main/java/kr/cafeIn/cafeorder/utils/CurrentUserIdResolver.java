package kr.cafeIn.cafeorder.utils;


import kr.cafeIn.cafeorder.annotation.CurrentUserId;
import kr.cafeIn.cafeorder.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * CurrentUserIdResolver.
 *
 * {@link CurrentUserId}가 컨트롤러 메서드 파라미터에 있을 때 바인딩 처리. HandlerMethodArgumentResolver는 컨트롤러 메서드에서 특정
 * 조건에 맞는 파라미터가 있을 때 값을 바인딩할 수 있는 인터페이스. HandlerMethodArgumentResolver를 이용해서 해당 로그인 정보를 컨트롤러의 메소드
 * 인자로 바로 주입할 수 있습니다. 이렇게 하면, 컨트롤러에서는 세션 값을 직접 참조하지 않고도 바인딩된 객체를 통해 쉽게 로그인 정보를 사용할 수 있습니다 ex)
 * @RequestBody, @PathVariable 들도 HandlerMethodArgumentResolver는를 통해 바인딩 됨.
 *
 */
@Component
@RequiredArgsConstructor
public class CurrentUserIdResolver implements HandlerMethodArgumentResolver {

  private final LoginService loginService;

  //HandlerMethodArgumentResolver 사용시 오버라이딩 해야 하는 메소드
  @Override
  public boolean supportsParameter(
      MethodParameter methodParameter) {//현재 파라미터를 resolver 이 지원하는지에 대한 true/false 값 리턴
    return methodParameter.hasParameterAnnotation(CurrentUserId.class);
  }

  @Override
  public Object resolveArgument(MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) //실제로 바인딩을 할 객체를 리턴 한다.
  {
    return loginService.getCurrentUser();
  }
}