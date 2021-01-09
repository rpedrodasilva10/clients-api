package br.clientsapi.clientsapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        return new ResponseEntity<>(ex.getApiResponse(),
                HttpStatus.valueOf(ex.getApiResponse().getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiException apiException = new ApiException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(apiException.getApiResponse(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
