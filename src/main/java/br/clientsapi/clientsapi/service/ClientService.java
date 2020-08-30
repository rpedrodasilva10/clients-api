package br.clientsapi.clientsapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import br.clientsapi.clientsapi.entity.Client;

/**
 * ClientService
 */
@Service
public class ClientService {

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        Client clientOne = Client.builder().name("Renan").surname("Silva").email("rpedrodasilva10@gmail.com").build();
        Client clientTwo = Client.builder().name("Priscila").surname("Silva").email("prisilva@gmail.com").build();
        Client clientThree = Client.builder().name("Ricardo").surname("Oliveira").email("roliveira@gmail.com").build();

        clients.add(clientOne);
        clients.add(clientTwo);
        clients.add(clientThree);

        return clients;

    }
}