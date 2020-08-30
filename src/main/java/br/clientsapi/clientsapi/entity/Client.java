package br.clientsapi.clientsapi.entity;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {

    private String name;
    private String surname;
    private String email;
}