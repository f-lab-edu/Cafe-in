
package kr.cafeIn.cafeorder.repository;

import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisUserRepository implements UserRepository {
    private  final UserMapper userMapper;

    @Override
    public void updateUser(Long id) {
        userMapper.updateUser(id);

    }

    @Override
    public Optional<User> findById(Long id) {
        userMapper.findById(id);
        return userMapper.findById(id);
    }




    @Override
    public User save(User users) {
        log.info("userMapper class={}", userMapper.getClass());
        userMapper.save(users);
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        userMapper.findByEmail(email);
        return userMapper.findByEmail(email);
    }


}

