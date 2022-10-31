package shop.kokodo.promotionservice.controller.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestResponse<T> {

    private int code;
    private String message;
    private T result;

    @Builder
    public RestResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
