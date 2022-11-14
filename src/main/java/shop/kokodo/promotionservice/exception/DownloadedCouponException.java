package shop.kokodo.promotionservice.exception;

public class DownloadedCouponException extends RuntimeException{

    public DownloadedCouponException(){}

    public DownloadedCouponException(String str){
        super(str);
    }
}
