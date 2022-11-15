package shop.kokodo.promotionservice.exception;

public class NoMemberException extends RuntimeException{

    public NoMemberException (){}

    public NoMemberException(String msg){ super(msg); }
}
