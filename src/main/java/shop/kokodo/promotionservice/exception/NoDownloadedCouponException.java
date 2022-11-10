package shop.kokodo.promotionservice.exception;

public class NoDownloadedCouponException extends RuntimeException{

    public NoDownloadedCouponException(){}

    public NoDownloadedCouponException(String msg) {super(msg);}
}
