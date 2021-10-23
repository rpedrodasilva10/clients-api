package br.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse {

    private Integer code;
    private String message;
    private String description;
    private List<ApiError> errors;
}
