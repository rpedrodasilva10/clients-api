package br.clientsapi.clientsapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.clientsapi.clientsapi.entity.Client;
import br.clientsapi.clientsapi.repository.ClientRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * ClientService
 */
@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Optional<Client> getClientById(Long clientId) {
        return repository.findById(clientId);
    }

    public Client createClient(Client newClient) {
        return repository.save(newClient);
    }

    public void deleteClient(Long clientId) {
        repository.delete(repository.findById(clientId).get());
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