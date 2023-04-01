package kr.cafeIn.cafeorder.exception;


/**
 *
 * 회원 탈퇴 후 3일간 회원가입 불가  Exception
 *
 */


public class RegistrationException extends Exception {
    public RegistrationException(String msg){
        super(msg);
    }
}
