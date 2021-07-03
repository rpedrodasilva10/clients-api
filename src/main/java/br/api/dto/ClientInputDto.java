package br.api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClientInputDto {
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
}
