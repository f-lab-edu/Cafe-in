package kr.cafeIn.cafeorder.exception;

/**
 * 404 Not Found Exception
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String msg) {

    super(msg);
  }
}
