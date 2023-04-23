package kr.cafeIn.cafeorder.mapper;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  void save(User user);

  Optional<User> findByEmail(String email);

  void updateUser(Long id);

  Optional<User> findById(Long id);



  void updatePassword(@Param("id") Long id,@Param("newPassword") String newPassword );

  User updateWithdrawnAt(@Param("id") Long id,@Param("withdrawnAt") LocalDateTime withdrawnAt);

}
