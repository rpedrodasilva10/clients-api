package br.clientsapi.clientsapi.dto;

import java.time.LocalDate;


import br.clientsapi.clientsapi.entity.Client;
import lombok.Data;

@Data
public class ClientInputDto {
    private String name;
    private String email;
    private LocalDate birthDate;

    public Client toClient(ClientInputDto dto) {
        return Client.builder().email(dto.getEmail()).birthDate(dto.getBirthDate()).name(dto.getName())
                .build();
    }
}
