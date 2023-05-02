package kr.cafeIn.cafeorder.exception;

/**
 * 이메일 전송 중 발생 하는 에러
 * (이메일서비스와의 통신 문제 , 잘못된 이메일 주소 등 문제로 발생)
 */

public class EmailMessagingException extends RuntimeException {

  public EmailMessagingException(String msg) {

    super(msg);
  }
}
