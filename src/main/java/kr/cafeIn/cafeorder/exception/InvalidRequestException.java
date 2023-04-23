package kr.cafeIn.cafeorder.exception;

/**
 * OPT 가 잘못된 경우
 */

public class InvalidRequestException extends RuntimeException {

  public InvalidRequestException(String msg) {

    super(msg);
  }
}
