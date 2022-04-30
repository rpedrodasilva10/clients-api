package br.api.service;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.ApiException;
import br.api.exception.custom.ClientNotFoundException;
import br.api.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClientService {


    private final ClientRepository repository;
    private final ModelMapper mapper;
    private final ObjectMapper jsonObjectMapper;
    private final MailSenderService mailSenderService;

    public List<Client> getAllClients() {
        log.info("[getAllClients] :: Listando todos os clientes");
        return repository.findAll();
    }

    public Client getClientById(Long clientId) throws ApiException {
        log.info("[getClientById] :: Buscando cliente com ID: {}", clientId);
        Optional<Client> optClient = this.findClientByIdOrThrowClientNotFound(clientId);
        return optClient.get();
    }

    public Client createClient(ClientInputDto inputDto) throws ApiException {
        log.info("[createClient] :: Criando cliente com body: {}",
                jsonObjectMapper.convertValue(inputDto, Client.class));
        Client newClient = mapper.map(inputDto, Client.class);
        Client savedClient = repository.save(newClient);

        this.mailSenderService.sendMail(savedClient.getEmail());

        return savedClient;
    }

    public void deleteClient(Long clientId) throws ApiException {
        log.info("[deleteClient] :: Deletando cliente com ID: {}", clientId);
        Optional<Client> optClient = this.findClientByIdOrThrowClientNotFound(clientId);

        repository.delete(optClient.get());
    }

    public Client updateClient(long clientId, ClientInputDto inputDto) throws ApiException {
        log.info("[updateClient] :: Atualizando cliente com ID: {}", clientId);
        Client clientData = mapper.map(inputDto, Client.class);
        Optional<Client> optionalClient = this.findClientByIdOrThrowClientNotFound(clientId);

        Client targetClient = optionalClient.get();
        targetClient.setBirthDate(clientData.getBirthDate());
        targetClient.setName(clientData.getName());
        targetClient.setEmail(clientData.getEmail());
        targetClient.setSurname(clientData.getSurname());

        return repository.save(targetClient);
    }


    private Optional<Client> findClientByIdOrThrowClientNotFound(Long clientId) throws ClientNotFoundException {
        Optional<Client> optionalClient = repository.findById(clientId);

        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(clientId);
        }

        return optionalClient;
    }
}