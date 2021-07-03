package br.api.service;

import br.api.entity.Client;
import br.api.exception.ApiException;
import br.api.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ClientService
 */
@Service
@Slf4j
public class ClientService {

    @Autowired
    ClientRepository repository;

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client getClientById(Long clientId) throws ApiException {
        Optional<Client> optClient = repository.findById(clientId);
        if (optClient.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), "Dados de entrada inválidos!", "Cliente não encontrado", new Exception());
        }
        return optClient.get();

    }

    public Client createClient(Client newClient) {
        return repository.save(newClient);
    }

    public void deleteClient(Long clientId) throws ApiException {
        try {
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

    public Client updateClient(Client clientData) {
        Optional<Client> optionalClient = repository.findById(clientData.getId());
        if (optionalClient.isPresent()) {
            Client targetClient = optionalClient.get();
            targetClient.setBirthDate(clientData.getBirthDate());
            targetClient.setName(clientData.getName());
            targetClient.setEmail(clientData.getEmail());
            targetClient.setSurname(clientData.getSurname());

            return repository.save(targetClient);
        }
        return null;
    }

}