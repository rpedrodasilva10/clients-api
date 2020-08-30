package br.clientsapi.clientsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.clientsapi.clientsapi.entity.Client;
import br.clientsapi.clientsapi.service.ClientService;

/**
 * ClientController
 */
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public List<Client> listClients() {

        return clientService.getAllClients();
    }

    @PostMapping
    public Client createClient(@RequestBody Client newClient) {
        return clientService.createClient(newClient);
    }
}