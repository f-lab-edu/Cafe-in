package kr.cafeIn.cafeorder.service;


import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.model.dto.request.LoginReq;
import kr.cafeIn.cafeorder.utils.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final String USER_ID = "USER_ID";

    private final HttpSession session;

    private final UserService userService;

    /**
     * 로그인. 데이터베이스에 저장된 패스워드와 입력한 패스워드 검증 후 세션에 값 저장.
     *
     *
     */


    public void login(LoginReq loginReq) {
        Optional<User> user = userService.findOne(loginReq.getEmail());

        if (PasswordEncrypt.isMatch(loginReq.getPassword(), user.get().getPassword())) {
            session.setAttribute(USER_ID, user.get().getId());


        } else {
            //런타임시 , 적절하지 않은 패스워드 오류 발생
            throw new IllegalArgumentException("비밀번호가 유효하지 않습니다");

        }


    }


    public void logout() {
        session.removeAttribute(USER_ID);

    }


    public Long getCurrentUser() {

        return (Long) session.getAttribute(USER_ID);
    }


}
