package kr.cafeIn.cafeorder.utils;

import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SessionManager {

  private static final String USER_ID = "USER_ID";

  private final HttpSession session;


  public Long getCurrentUser() {
    return (Long) session.getAttribute(USER_ID);
  }

  public void logout() {
    session.removeAttribute(USER_ID);
  }

  public void login(Long id) {
    session.setAttribute(USER_ID, id);
  }

}
