package br.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {
    private final String code;
    private final String message;
    private final String nativeMessage;
}
