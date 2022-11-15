package shop.kokodo.promotionservice.exception;

public class NoProductException extends RuntimeException{

    public NoProductException(){}

    public NoProductException(String msg){
        super(msg);
    }
}
