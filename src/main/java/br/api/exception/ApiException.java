package br.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ApiException extends Exception {
    private static final long serialVersionUID = -4315650330185909419L;
    private final Integer code;
    private final String message;
    private final String nativeMessage;
    private final transient ApiResponse apiResponse;

    public ApiException(Integer code, String message, String nativeMessage, Exception ex) {
        super(nativeMessage, ex);
        this.code = code;
        this.message = message;
        this.nativeMessage = nativeMessage;
        this.apiResponse = new ApiResponse(code, message, nativeMessage);

        ex.printStackTrace();
    }

}
