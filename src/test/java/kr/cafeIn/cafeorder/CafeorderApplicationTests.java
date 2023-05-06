package kr.cafeIn.cafeorder;

import java.time.LocalDateTime;
import kr.cafeIn.cafeorder.mapper.UserMapper;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CafeorderApplicationTests {

  @Autowired
  UserService userService;

  @Autowired
  UserMapper userMapper;

  @Test
  void contextLoads() {

    Long suffix = System.currentTimeMillis() % 100000L;
    User sample = new User(
        null, String.format("test+%s@email.com", suffix),
        "password", "nickname", 1, 0,
        LocalDateTime.now(), LocalDateTime.now(), null

    );
    userMapper.save(sample);

  }

}
