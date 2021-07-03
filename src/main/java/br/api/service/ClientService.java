package br.api.service;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.ApiException;
import br.api.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new ApiException(HttpStatus.NOT_FOUND.value(), "Dados de entrada inválidos!", "Cliente não encontrado", new Exception());
        }
        return optClient.get();

    }

    public Client createClient(ClientInputDto inputDto) throws JsonProcessingException {
        log.info("[createClient] :: Criando cliente com body: {}", jsonObjectMapper.writeValueAsString(inputDto));
        Client newClient = mapper.map(inputDto, Client.class);
        return repository.save(newClient);
    }

    public void deleteClient(Long clientId) throws ApiException {
        try {
            log.info("[deleteClient] :: Deletando cliente com ID: {}", clientId);
            Optional<Client> optClient = repository.findById(clientId);
            if (optClient.isPresent()) {
                repository.delete(optClient.get());
            } else {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Cliente não encontrado.", "Não foi possível localizar o cliente", new Exception());
            }
        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Não foi possível deletar o recurso.", e.getLocalizedMessage(), e);
        }
    }

    public Client updateClient(long id, ClientInputDto inputDto) throws ApiException {
        try {
            log.info("[updateClient] :: Atualizando cliente com ID: {}", id);
            Client clientData = mapper.map(inputDto, Client.class);
            Optional<Client> optionalClient = repository.findById(id);
            if (optionalClient.isPresent()) {
                Client targetClient = optionalClient.get();
                targetClient.setBirthDate(clientData.getBirthDate());
                targetClient.setName(clientData.getName());
                targetClient.setEmail(clientData.getEmail());
                targetClient.setSurname(clientData.getSurname());

                return repository.save(targetClient);
            }
            return null;
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Não foi possível atualizar o recurso.", e.getLocalizedMessage(), e);
        }
    }

}