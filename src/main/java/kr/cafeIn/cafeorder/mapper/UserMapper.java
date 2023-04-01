package kr.cafeIn.cafeorder.mapper;

import kr.cafeIn.cafeorder.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    void save(User user);

    Optional<User>findByEmail(String email);

    void updateUser(Long id);

    Optional<User>findById(Long id);




}
