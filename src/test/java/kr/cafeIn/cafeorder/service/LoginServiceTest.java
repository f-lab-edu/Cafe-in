package kr.cafeIn.cafeorder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginRequest;
import kr.cafeIn.cafeorder.utils.PasswordEncrypt;
import kr.cafeIn.cafeorder.utils.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

@ExtendWith(MockitoExtension.class)// mockito를 사용함을 명시
@Slf4j
class LoginServiceTest {

  @Mock
  UserService userService;


  @InjectMocks //@mock객체를 주입
  LoginService loginService;

  User user;

  LoginRequest loginReq;

  @BeforeEach
  public void beforeEach() {
    injectSessionInUserService();

    user = User.builder()
        .id(1L)
        .email("email@email.com")
        .password(PasswordEncrypt.encrypt("password"))
        .nickname("name")
        .grade(1)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .withdrawnAt(null)
        .build();

    loginReq = LoginRequest.builder()
        .email("email@email.com")
        .password("password")
        .build();


  }

  private void injectSessionInUserService() {

    try {
      Field sessionField = loginService.getClass()
          .getDeclaredField("sessionManager");//private 필드 접근(리플랙션)
      sessionField.setAccessible(true);//값 변경 가능
      // HTTP 세션을 테스트 하기 위한 MockHttpSession 클래스를  주입
      SessionManager sessionManager = new SessionManager(new MockHttpSession());
      sessionField.set(loginService, sessionManager);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

  }


  @Test
  @DisplayName("로그인성공")
  public void loginTestWhenSuccess() {
    when(userService.findOne(loginReq.getEmail())).thenReturn(Optional.ofNullable(user));
    Optional<User> userOptional = userService.findOne(loginReq.getEmail());
    log.info(String.valueOf(userOptional.get()));

    loginService.login(loginReq);
    assertEquals(loginService.getCurrentUser(), user.getId());

  }

  @Test
  @DisplayName("로그인 실패 : 잘못된 패스워드")
  public void loginTestWhenFail() {
    when(userService.findOne(loginReq.getEmail())).thenReturn(Optional.ofNullable(user));
    Optional<User> userOptional = userService.findOne(loginReq.getEmail());
    log.info(String.valueOf(userOptional.get()));
    loginReq.setPassword("");
    assertThrows(IllegalArgumentException.class, () -> loginService.login(loginReq));

    loginReq.setPassword("password" + "@");
    assertThrows(IllegalArgumentException.class, () -> loginService.login(loginReq));

    loginReq.setPassword("password".substring(1));
    assertThrows(IllegalArgumentException.class, () -> loginService.login(loginReq));

    loginReq.setPassword(PasswordEncrypt.encrypt("password"));
    assertThrows(IllegalArgumentException.class, () -> loginService.login(loginReq));


  }

  @Test
  @DisplayName("로그아웃")
  public void logoutTestWhenSuccess() {
    loginService.logout();
    assertNull(loginService.getCurrentUser());

  }


}