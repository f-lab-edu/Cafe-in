package kr.cafeIn.cafeorder.exception;

/**
 * OPT 가 잘못된 경우
 */

public class InvalidOtpException extends RuntimeException {

  public InvalidOtpException(String msg) {

    super(msg);
  }
}
