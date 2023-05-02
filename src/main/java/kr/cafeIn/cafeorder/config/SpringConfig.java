package kr.cafeIn.cafeorder.config;


import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
  /**
   * GoogleAuthenticator  클래스 인스턴스  spring 에 등록
   * @return
   */
  @Bean
  public GoogleAuthenticator googleAuthenticator(){
    return  new GoogleAuthenticator();
  }

}

