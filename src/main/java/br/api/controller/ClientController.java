package br.api.controller;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.ApiException;
import br.api.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/clients")
@Slf4j
public class ClientController {
    //(value = "/api/v1/clients")

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ClientService clientService;

    @GetMapping({"", "/"})
    public List<Client> listClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) throws ApiException {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody ClientInputDto clientInputDto) throws ApiException {
        return clientService.createClient(clientInputDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) throws ApiException {
        this.clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable long id,
                                               @RequestBody ClientInputDto clientDto) throws ApiException {
        Client targetClient = this.clientService.updateClient(id, clientDto);
        if (targetClient != null) {
            return new ResponseEntity<>(targetClient, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}