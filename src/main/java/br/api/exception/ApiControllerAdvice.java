package br.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        return new ResponseEntity<>(ex.getApiResponse(),
                HttpStatus.valueOf(ex.getApiResponse().getCode()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException typeMismatchException) {
        String errorDescription = String
                .format("Valor %s inválido para o parâmetro '%s' (%s)", typeMismatchException.getValue(),
                        typeMismatchException.getName(), typeMismatchException.getRequiredType());
        ApiException apiException = new ApiException(
                HttpStatus.BAD_REQUEST.value(),
                errorDescription, typeMismatchException.getLocalizedMessage(), typeMismatchException, new ArrayList<>());
        return new ResponseEntity<>(apiException.getApiResponse(),
                HttpStatus.valueOf(apiException.getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        List<ApiError> errorList = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errorList.add(new ApiError(error.getCode(), error.getDefaultMessage(), error.getField()))
        );

        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST.value(), "Dados de entrada inválidos!", "Falha na validação dos dados informados", ex, errorList);

        return new ResponseEntity<>(apiException.getApiResponse(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiException apiException = new ApiException(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(apiException.getApiResponse(),
                HttpStatus.valueOf(apiException.getCode()));
    }
}
