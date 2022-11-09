package shop.kokodo.promotionservice.exception;

import java.io.Serializable;

public class NoSellerException extends RuntimeException {

    public NoSellerException(){};

    public NoSellerException(String msg){
        super(msg);
    }

}
