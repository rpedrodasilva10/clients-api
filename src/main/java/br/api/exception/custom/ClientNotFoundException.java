package br.api.exception.custom;


import br.api.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends ApiException {
    private static final long serialVersionUID = -6691189576968465399L;
    private static final String DEFAULT_NOT_FOUND_MESSAGE = "Cliente não encontrado com o ID: %s";

    public ClientNotFoundException(Long clientId) {
        super(HttpStatus.BAD_REQUEST.value(), "Falha na Operação! Cliente não encontrado",
                String.format(DEFAULT_NOT_FOUND_MESSAGE, clientId));
    }
}
