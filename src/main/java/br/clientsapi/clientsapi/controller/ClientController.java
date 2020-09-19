package br.clientsapi.clientsapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.clientsapi.clientsapi.entity.Client;
import br.clientsapi.clientsapi.service.ClientService;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * ClientController
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public List<Client> listClients() {


        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        Optional<Client> optClient = clientService.getClientById(clientId);

        if (optClient.isPresent()) {
            return ResponseEntity.ok(optClient.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@RequestBody Client newClient) {
        return clientService.createClient(newClient);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long clientId) {
        this.clientService.deleteClient(clientId);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@RequestBody Client newData) {
        Client targetClient = this.clientService.updateClient(newData);
        if (targetClient != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}