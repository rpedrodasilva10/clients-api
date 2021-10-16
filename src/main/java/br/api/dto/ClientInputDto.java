package br.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ClientInputDto {

    @NotBlank(message = "O nome é obrigatório")
    private String name;
    @NotBlank(message = "O sobrenome é obrigatório")
    private String surname;

    @Email(message = "O e-mail '${validatedValue}' não é válido")
    @NotNull(message = "O e-mail é obrigatório")
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate birthDate;
}
