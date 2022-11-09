package shop.kokodo.promotionservice.exception;

public class DuplicateCouponNameException extends RuntimeException{

    public DuplicateCouponNameException(){}

    public DuplicateCouponNameException(String msg){
        super(msg);
    }
}
