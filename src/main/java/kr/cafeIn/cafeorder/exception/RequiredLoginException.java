package kr.cafeIn.cafeorder.exception;

/**
 * 401 로그인을 하지않고 로그인이 필요한 서비스에 접근할때 발생하는 Exception.
 */
public class RequiredLoginException extends RuntimeException {

  public RequiredLoginException(String msg) {
    super(msg);
  }
}