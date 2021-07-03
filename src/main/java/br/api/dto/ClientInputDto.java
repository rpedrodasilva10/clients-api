package br.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientInputDto {
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
}
