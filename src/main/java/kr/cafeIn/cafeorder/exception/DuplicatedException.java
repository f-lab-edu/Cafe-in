package kr.cafeIn.cafeorder.exception;

/**
 * 409 중복으로 인해 발생하는 Exception.
 */
public class DuplicatedException extends RuntimeException {

  public DuplicatedException(String msg) {

    super(msg);
  }
}
