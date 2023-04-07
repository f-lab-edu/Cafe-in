package kr.cafeIn.cafeorder.service;


import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.exception.RegistrationException;
import kr.cafeIn.cafeorder.exception.WithdrawalException;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service //스프링 컨테이너에 등록을 한다 .
public class UserService {

  private final UserRepository userRepository;


  @Autowired
  public UserService(@Qualifier("myBatisUserRepository") UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  /**
   * 회원가입
   */

  public String join(User user) {

    //중복회원 검증
    validateDuplicateUser(user);
    userRepository.save(user);//통과시 저장
    return user.getEmail();//회원가입 하면 id만 반환해 준다


  }

  private void validateDuplicateUser(User users) {//같은 이름이 있는 중복 회원 x
    userRepository.findByEmail(users.getEmail())
        .ifPresent(m -> {// user에 이미 값이 있으면
          throw new DuplicatedException("이미 존재하는 회원입니다");
        });
  }


  /**
   * Email 일치하는 유저 검색. 엔티티를 읽기 전용으로 조회하면, 변경 감지를 위한 스냅샷을 유지하지 않아도 되고 , 영속성 컨텍스트를 플러시 하지 않아도 돼 성능을
   * 최적화 할 수 있다.
   *
   * @param email 이메일
   */

  @Transactional(readOnly = true)
  public Optional<User> findOne(String email) {

    return Optional.ofNullable(userRepository.findByEmail(email)
        //값이 없다면 NotFoundException 던지기

        .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자 입니다")));
  }

  /**
   * id에 해당하는 유저 탈퇴 정책: 탈퇴 후 3일동안 회원가입 불가
   *
   * @param id 유저 ID
   * @since 1.0.0
   */
  @Transactional
  public void withdraw(Long id) throws WithdrawalException {
    Optional<User> userOptional = userRepository.findById(id);
    if (!userOptional.isPresent()) {
      throw new WithdrawalException("회원을 찾을 수 없습니다");
    }
    User user = userOptional.get();
    if (user.getWithdrawnAt() != null) {
      throw new WithdrawalException("이미 탈퇴한 회원입니다");
    }

    //withdrawnAt 필드를 현재 시간으로 설정
    user.setWithdrawnAt(LocalDateTime.now());
    userRepository.save(user);
  }

  @Transactional
  public void register(Optional<User> user) throws RegistrationException {
    Optional<User> existingUser = userRepository.findByEmail(user.get().getEmail());
    if (existingUser != null && existingUser.get().getWithdrawnAt().plusDays(3)
        .isAfter(LocalDateTime.now())) {
      throw new RegistrationException("탈퇴 후 3일 이내 등록 불가합니다");
    }
    //탈퇴 후 3일이 지났을 경우 ,회원가입 가능(기존회원에 wthdrawnAt 를 null 로 업데이트 )
    Long id = existingUser.get().getId();
    userRepository.updateUser(id);

  }


}
