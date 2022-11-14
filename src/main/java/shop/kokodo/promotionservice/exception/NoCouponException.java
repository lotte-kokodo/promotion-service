package shop.kokodo.promotionservice.exception;

public class NoCouponException extends RuntimeException{

    public NoCouponException(){}
    public NoCouponException(String msg){
        super(msg);
    }
}
