package kr.cafeIn.cafeorder.repository;


import kr.cafeIn.cafeorder.model.domain.User;

import java.util.Optional;

public interface UserRepository {

  User save(User users);

  Optional<User> findByEmail(String email);// email 으로 찾을 경우

  void updateUser(Long id);

  Optional<User> findById(Long id);


}
