package br.clientsapi.clientsapi.dto;

import java.time.LocalDate;


import br.clientsapi.clientsapi.entity.Client;
import lombok.Data;

@Data
public class ClientInputDto {
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;

    public Client toClient() {
        return Client.builder().surname(surname).email(this.getEmail()).birthDate(this.getBirthDate()).name(this.getName())
                .build();
    }
}
