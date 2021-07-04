package br.api.controller;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.ApiException;
import br.api.service.ClientService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClientController
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/clients")
@Slf4j
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
  public Client createClient(@RequestBody ClientInputDto clientInputDto) throws ApiException {
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