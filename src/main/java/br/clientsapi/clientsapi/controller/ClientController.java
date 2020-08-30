package br.clientsapi.clientsapi.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.clientsapi.clientsapi.entity.Client;

/**
 * ClientController
 */
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @GetMapping
    public List<Client> listClients() {

        List<Client> clients = new ArrayList<>();
        Client clientOne = Client.builder().name("Renan").surname("Silva").email("rpedrodasilva10@gmail.com").build();
        Client clientTwo = Client.builder().name("Priscila").surname("Silva").email("pridelicia@gmail.com").build();

        clients.add(clientOne);
        clients.add(clientTwo);

        return clients;

    }
}