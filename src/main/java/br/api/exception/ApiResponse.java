package br.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse {

    private Integer code;
    private String message;
    private String description;
    private List<ApiError> errors;
}
