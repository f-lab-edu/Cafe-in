package kr.cafeIn.cafeorder.exception;


/**
 *
 * 회원 탈퇴  Exception
 *
 */


public class WithdrawalException extends Exception {
    public WithdrawalException(String msg){
        super(msg);
    }
}
