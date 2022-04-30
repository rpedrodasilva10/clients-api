package br.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ApiError> errors = new ArrayList<>();
        result.getFieldErrors().parallelStream().forEach(fieldError ->
                errors.add(new ApiError(fieldError.getCode(), fieldError.getDefaultMessage())));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .code(1)
                                .message("Corpo da requisição inválido!")
                                .errors(errors)
                                .description("Falha na validação estrutural! Verifique a lista de erros para mais detalhes").build()


                );
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
