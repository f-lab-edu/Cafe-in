
package kr.cafeIn.cafeorder.service;

import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional//테스트를 실행할때 트렌젝션을 실행하고 , 다 끝나면 롤백을 시켜준다.
class UserServiceTest {

    @Autowired
    UserService userService ;
    @Qualifier("myBatisUserRepository")
    @Autowired
    UserRepository userRepository;

    @Test
    void 회원가입() {
        //given
        User user = new User(
                null, "test7@email.com",
                "password", "nickname", 1, 0,
                LocalDateTime.now(), LocalDateTime.now(),null
        );

        //when(저장한 아이디가 나와야 한다)
        String saveEmail = userService.join(user);

        //then
        User findUser = userService.findOne(saveEmail).get();
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());


    }

    @Test
    void findOne() {
    }


    @Test
    public void 중복_회원_예외() {

        // given
        User user = new User(
                null, "test8@email.com",
                "password", "nickname", 1, 0,
                LocalDateTime.now(), LocalDateTime.now(),null
        );

        User user1 = new User(
                null, "test8@email.com",
                "password", "nickname2", 1, 1000,
                LocalDateTime.now(), LocalDateTime.now(),null


        );

        // when
        userService.join(user);
        DuplicatedException e = assertThrows(DuplicatedException.class, () -> userService.join(user1));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");


    }

}
