package kr.cafeIn.cafeorder.service;

import kr.cafeIn.cafeorder.annotation.WithdrawalCheck;
import kr.cafeIn.cafeorder.exception.RegistrationException;
import kr.cafeIn.cafeorder.exception.WithdrawalException;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginReq;
import kr.cafeIn.cafeorder.repository.UserRepository;
import kr.cafeIn.cafeorder.utils.PasswordEncrypt;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest2 {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserService userService;


  User user;


  @BeforeEach
  public void beforeEach() {

    user = User.builder()
        .id(1L)
        .email("email@email.com")
        .password(PasswordEncrypt.encrypt("password"))
        .nickname("name")
        .grade(1)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .withdrawnAt(null)
        .build();


  }

  @Test
  @DisplayName("회원탈퇴 후 재가입 기간 미달 ")
  public void WithdrawnLessThan3DaysAgo() {
    //given
    //탈퇴 날짜가 2일 전으로 설정된 Member 객체를 생성

    user.setWithdrawnAt(LocalDateTime.now().minusDays(2));

    //when
    // user 를 반환하도록 userRepository.findByEmail 메서드를 호출
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    //then
    // 탈퇴한 회원의 이메일로 가입 메소드를 호출하면 RegistrationException 이 발생합니다.
    RegistrationException e = Assert.assertThrows(RegistrationException.class,
        () -> userService.register(Optional.ofNullable(user)));

    assertThat(e.getMessage()).isEqualTo("탈퇴 후 3일 이내 등록 불가합니다");


  }

  @Test
  @DisplayName("회원탈퇴 후 재가입 기간 초과  ")
  public void WithdrawnMoreThan3DaysAgo() throws WithdrawalException, RegistrationException {

    //given
    user.setWithdrawnAt(LocalDateTime.now().minusDays(4));

    //when
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    //then
    userService.register(Optional.ofNullable(user));

    verify(userRepository).updateUser(user.getId());


  }


  @Test
  @DisplayName("회원탈퇴:성공")
  public void testWithdraw_Success() throws WithdrawalException {
    //given

    Long id = user.getId();

    //when
    //Optional 객체가 user 객체가 일치하는 지 검증
    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    //withdrawnAt 필드 값 null-> LocalDateTime 으로 변경
    userService.withdraw(id);

    //then
    //WithdrawmAt 칼럼이 null 인지 확인,userRepository.save()메소드 호출 확인
    assertNotNull(user.getWithdrawnAt());
    verify(userRepository).save(user);


  }

  @Test
  @DisplayName("회원탈퇴: 존재하지 않는 회원")
  public void testWithdraw_MemberNotFound() {
    //given

    Long id = user.getId();

    //when
    when(userRepository.findById(id)).thenReturn(Optional.empty());

    // then
    // WithdrawalException이 발생하면 테스트 통과

    WithdrawalException e = Assert.assertThrows(WithdrawalException.class,
        () -> userService.withdraw(id));

    assertThat(e.getMessage()).isEqualTo("회원을 찾을 수 없습니다");

  }

  @Test
  @DisplayName("회원탈퇴: 이미 탈퇴한 회원")
  public void testWithdraw_AlreadyWithdrawn() {
    //given

    user.setWithdrawnAt(LocalDateTime.now());
    Long id = user.getId();

    //when
    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    // then
    // WithdrawalException 이 발생하면 테스트 통과

    WithdrawalException e = Assert.assertThrows(WithdrawalException.class,
        () -> userService.withdraw(id));

    assertThat(e.getMessage()).isEqualTo("이미 탈퇴한 회원입니다");

  }


}