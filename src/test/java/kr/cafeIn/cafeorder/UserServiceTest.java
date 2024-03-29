package kr.cafeIn.cafeorder;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class UserServiceTest {

  UserService userService;//userService를 만들어 주면서 의존성 넣어줘야 한다. mocking을 해준다. (TDD 를 하기위해서는 테스트를 하기 위해서는 )

  @Mock
  User user;

  @Test
  @DisplayName("회원가입에 성공합니다")
  public void join_성공케이스() {
    // given
    user = new User(
        null, "test@email.com",
        "password", "nickname", 1, 0,
        LocalDateTime.now(), LocalDateTime.now(), null

    );

    // when
    String email = userService.join(user);

    // then
    assertEquals(user.getEmail(), email);

  }

  @Test
  @DisplayName("회원가입에 실패합니다:중복된 이메일")
  public void join_실패케이스() {

    // given
    User user = new User(
        null, "testFailed@email.com",
        "password", "nickname", 1, 0,
        LocalDateTime.now(), LocalDateTime.now(), null
    );

    System.out.println(user);
    // when
    DuplicatedException e = assertThrows(DuplicatedException.class, () -> userService.join(user));
    //then
    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

  }


}
