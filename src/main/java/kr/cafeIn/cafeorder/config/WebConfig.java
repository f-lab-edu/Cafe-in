package kr.cafeIn.cafeorder.config;

import java.util.List;
import kr.cafeIn.cafeorder.utils.CurrentUserIdResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig. CurrentUserIdResolver 추가를 위한 설정. resolver 등록
 */
@Configuration //설정파일,Bean 에 등록(스프링에 등록)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final CurrentUserIdResolver currentUserIdResolver;

  //HandlerMethodArgumentResolver 를 상속받은 객체를 가지고 루프를 돌며 지원하는 파라미터인지 확인 하고 결과 리턴
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(currentUserIdResolver);
  }
}