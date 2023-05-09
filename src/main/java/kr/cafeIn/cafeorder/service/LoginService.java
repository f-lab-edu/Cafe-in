package kr.cafeIn.cafeorder.service;


import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginRequest;
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

  public void login(LoginRequest loginReq) {
    User user = userService.findOne(loginReq.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다"));

    if (!PasswordEncrypt.isMatch(loginReq.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
    }

    sessionManager.login(user.getId());
  }

  public void logout() {
    sessionManager.logout();
  }

  //사용자 아이디를 세션에 저장
  public Long getCurrentUser() {
    return sessionManager.getCurrentUser();
  }


}
