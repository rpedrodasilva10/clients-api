package br.clientsapi.clientsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.clientsapi.clientsapi.entity.Client;
import br.clientsapi.clientsapi.repository.ClientRepository;

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

    public Client createClient(Client newClient) {
        return repository.save(newClient);
    }

}