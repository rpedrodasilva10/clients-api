package br.api.service;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.ApiException;
import br.api.exception.custom.ClientNotFoundException;
import br.api.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientService {

  @Autowired
  ClientRepository repository;

  @Autowired
  ModelMapper mapper;

  @Autowired
  ObjectMapper jsonObjectMapper;

  public List<Client> getAllClients() {
    log.info("[getAllClients] :: Listando todos os clientes");
    return repository.findAll();
  }

  public Client getClientById(Long clientId) throws ApiException {
    log.info("[getClientById] :: Buscando cliente com ID: {}", clientId);
    Optional<Client> optClient = repository.findById(clientId);
    if (optClient.isEmpty()) {
      throw new ClientNotFoundException(clientId);
    }
    return optClient.get();
  }

  public Client createClient(ClientInputDto inputDto) throws ApiException {
    final String defaultFailureMessage = "Não foi possível CRIAR o cliente!";
    try {
      log.info("[createClient] :: Criando cliente com body: {}",
          jsonObjectMapper.writeValueAsString(inputDto));
      Client newClient = mapper.map(inputDto, Client.class);
      return repository.save(newClient);
    } catch (JsonProcessingException jsonProcessingException) {
      throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), defaultFailureMessage,
          "Não foi possível processar o JSON de entrada", jsonProcessingException);
    }
  }

  public void deleteClient(Long clientId) throws ApiException {
    log.info("[deleteClient] :: Deletando cliente com ID: {}", clientId);
    Optional<Client> optClient = repository.findById(clientId);

    if (optClient.isEmpty()) {
      throw new ClientNotFoundException(clientId);
    }

    repository.delete(optClient.get());
  }

  public Client updateClient(long clientId, ClientInputDto inputDto) throws ApiException {
    log.info("[updateClient] :: Atualizando cliente com ID: {}", clientId);
    Client clientData = mapper.map(inputDto, Client.class);
    Optional<Client> optionalClient = repository.findById(clientId);

    if (optionalClient.isEmpty()) {
      throw new ClientNotFoundException(clientId);
    }

    Client targetClient = optionalClient.get();
    targetClient.setBirthDate(clientData.getBirthDate());
    targetClient.setName(clientData.getName());
    targetClient.setEmail(clientData.getEmail());
    targetClient.setSurname(clientData.getSurname());

    return repository.save(targetClient);
  }
}