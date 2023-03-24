package kr.cafeIn.cafeorder;


import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.repository.UserRepository;
import kr.cafeIn.cafeorder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {
    UserService userService;//userService를 만들어 주면서 의존성 넣어줘야 한다. mocking을 해준다. (TDD 를 하기위해서는 테스트를 하기 위해서는 )

    @BeforeEach
    public void setup() {
        System.out.println("setup");
        userService = new UserService(
                // TODO mocking
                new UserRepository() {
                    @Override
                    public User save(User users) {
                        return new User();
                    }

                    @Override
                    public Optional<User> findByEmail(String email) {
                        boolean isExist = email.equals("testFailed@email.com");//user를 만들어서 리턴한다
                        return isExist ?
                                Optional.of(
                                    User.builder()
                                            .email(email)
                                            .build()
                                ) : Optional.empty();
                    }
                }
        );
    }

    @Test
    public void join_성공케이스() {
        // given
        User user = new User(
                null, "test@email.com",
                "password", "nickname", 1, 0,
                LocalDateTime.now(), LocalDateTime.now()
        );

        // when
        String email = userService.join(user);

        // then
        assertEquals(user.getEmail(), email);

    }

    @Test
    public void join_실패케이스() {

       // given
        User user = new User(
                null, "testFailed@email.com",
                "password", "nickname", 1, 0,
                LocalDateTime.now(), LocalDateTime.now()
        );


        System.out.println(user);



        // when
       /*userService.join(user);*/

      IllegalStateException e =assertThrows(IllegalStateException.class, () -> userService.join(user));

       //then
        assertThat(e.getMessage()).isEqualTo("이미존재하는 회원입니다");


    }
}
