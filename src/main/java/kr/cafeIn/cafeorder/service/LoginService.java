package kr.cafeIn.cafeorder.service;


import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginReq;
import kr.cafeIn.cafeorder.utils.PasswordEncrypt;
import kr.cafeIn.cafeorder.utils.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private static final String USER_ID = "USER_ID";

  private final SessionManager sessionManager;
  private final UserService userService;

  /**
   * 로그인. 데이터베이스에 저장된 패스워드와 입력한 패스워드 검증 후 세션에 값 저장.
   */

  public void login(LoginReq loginReq) {
    Optional<User> user = userService.findOne(loginReq.getEmail());

    if (PasswordEncrypt.isMatch(loginReq.getPassword(), user.get().getPassword())) {
      sessionManager.login(user.get().getId());

    } else {
      //런타임시 , 적절하지 않은 패스워드 오류 발생
      throw new IllegalArgumentException("비밀번호가 유효하지 않습니다");
    }
  }

  public void logout() {
    sessionManager.logout();
  }

  //사용자 아이디를 세션에 저장
  public Long getCurrentUser() {
    return sessionManager.getCurrentUser();
  }


}
