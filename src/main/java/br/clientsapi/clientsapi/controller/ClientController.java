package br.clientsapi.clientsapi.controller;

import br.clientsapi.clientsapi.dto.ClientInputDto;
import br.clientsapi.clientsapi.entity.Client;
import br.clientsapi.clientsapi.exception.ApiException;
import br.clientsapi.clientsapi.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClientController
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ClientService clientService;

    @GetMapping
    public List<Client> listClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) throws ApiException {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@RequestBody ClientInputDto clientInputDto) {
        return clientService.createClient(modelMapper.map(clientInputDto, Client.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) throws ApiException {
        this.clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody ClientInputDto clientDto) {
        Client targetClient = this.clientService.updateClient(clientDto.toClient());
        if (targetClient != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}