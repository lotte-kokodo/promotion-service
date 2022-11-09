package shop.kokodo.promotionservice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.kokodo.promotionservice.controller.response.RestResponse;
import shop.kokodo.promotionservice.dto.response.Failure;
import shop.kokodo.promotionservice.dto.response.Response;
import shop.kokodo.promotionservice.exception.*;

import java.nio.channels.NoConnectionPendingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSellerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Failure handleNoSellerException(NoSellerException e){
        return new Failure("존재하지 않은 seller ID");
    }

    @ExceptionHandler(NoProductException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Failure handleNoProductException(NoProductException e){
        return new Failure("존재하지 않은 seller ID");
    }

    @ExceptionHandler(NoMemberException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Failure handleNoMemberException(NoMemberException e){
        return new Failure("존재하지 않은 seller ID");
    }

    @ExceptionHandler(DuplicateCouponNameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Failure handleDuplicateCouponNameException(DuplicateCouponNameException e){
        return new Failure("이미 존재하는 쿠폰 이름");
    }

    @ExceptionHandler(NoConnectionPendingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Failure handleNoConnectionPendingException(NoConnectionPendingException e){
        return new Failure("존재하지 않는 쿠폰");
    }

    @ExceptionHandler(DownloadedCouponException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Failure handleDownloadedCouponException(DownloadedCouponException e){
        return new Failure("이미 다운받은 쿠폰");
    }
}
