package kr.cafeIn.cafeorder.service;


import java.time.LocalDateTime;
import java.util.Optional;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.exception.RegistrationException;
import kr.cafeIn.cafeorder.exception.WithdrawalException;
import kr.cafeIn.cafeorder.mapper.UserMapper;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service //스프링 컨테이너에 등록을 한다 .
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;



  /**
   * 회원가입
   */
  public String join(User user) {
    //중복회원 검증
    validateDuplicateUser(user);
    user.setPassword(PasswordEncrypt.encrypt(user.getPassword()));
   userMapper.save(user);//통과시 저장
    return user.getEmail();//회원가입 하면 id만 반환해 준다
  }

  private void validateDuplicateUser(User users) {//같은 이름이 있는 중복 회원 x
    userMapper.findByEmail(users.getEmail())
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

  //@Transactional(readOnly =false)
  public Optional<User> findOne(String email) {

    return userMapper.findByEmail(email);
  }

  /**
   * id에 해당하는 유저 탈퇴 정책: 탈퇴 후 3일동안 회원가입 불가
   *
   * @param id 유저 ID
   * @since 1.0.0
   */
  //@Transactional
  public User withdraw(Long id) throws WithdrawalException {

    Optional<User> userOptional = userMapper.findById(id);
    if (userOptional.isEmpty()) {
      throw new WithdrawalException("회원을 찾을 수 없습니다");
    }
    User user = userOptional.get();
    if (user.getWithdrawnAt() != null) {
      throw new WithdrawalException("이미 탈퇴한 회원입니다");
    }

    //withdrawnAt 필드를 현재 시간으로 설정
/*    user.setWithdrawnAt(LocalDateTime.now());*/
   LocalDateTime withdrawnAt =LocalDateTime.now();
    log.info("withdrawnAt: {}, id: {}", withdrawnAt, id);
    userMapper.updateWithdrawnAt(id,withdrawnAt);
    return user;
  }

  /**
   * 탈퇴 후 3일이 지났는지 체크 하는 메서드
   *
   * @param user
   * @throws RegistrationException
   */

  //@Transactional
  public void register(Optional<User> user) throws RegistrationException {
    Optional<User> existingUser = userMapper.findByEmail(user.get().getEmail());
    if (existingUser.isEmpty()) {
      throw new RegistrationException("존재하지 않는 사용자입니다");
    }

    //탈퇴 후 3일이 지났을 경우 ,회원가입 가능(기존회원에 wthdrawnAt 를 null 로 업데이트 )
    if(existingUser.get().getWithdrawnAt().plusDays(3).isAfter(LocalDateTime.now())){
       throw  new RegistrationException("탈퇴 후 3일 이내 등록 불가합니다");
    }else{
      Long id = existingUser.get().getId();
      userMapper.updateUser(id);
    }



  }

  /**
   * 회원 비밀번호 변경 1.회원 email, nickname 체크를 한다. 2.opt가 db에 저장 되어있는 opt랑 맞는지 확인을 한다 . 맞을시는 패스워드 변경 가능
   * 3.새로운 패스워드를 암호화 진행뒤 해당 유저에 업데이트 한다.
   */


  public void updatePassword(Long userid,String newPassword){
    log.info("userid:{}",userid,"newPassword:{}",newPassword);
    userMapper.updatePassword(userid,newPassword);
  }


}
