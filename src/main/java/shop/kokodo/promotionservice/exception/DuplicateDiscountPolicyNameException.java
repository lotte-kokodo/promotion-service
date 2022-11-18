package shop.kokodo.promotionservice.exception;

/**
 * packageName : shop.kokodo.promotionservice.exception
 * fileName : DuplicateDiscountPolicyNameException
 * author : BTC-N24
 * date : 2022-11-17
 * description :
 * ======================================================
 * DATE                AUTHOR                NOTE
 * ======================================================
 * 2022-11-17           BTC-N24              최초 생성
 */
public class DuplicateDiscountPolicyNameException extends RuntimeException{

    public DuplicateDiscountPolicyNameException(){}

    public DuplicateDiscountPolicyNameException(String msg){
        super(msg);
    }
}
