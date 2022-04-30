package br.api.service;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.custom.ClientNotFoundException;
import br.api.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientService.class)
@ComponentScan(value = "br.api")
class ClientServiceTest {

    private final String API_BASE_PATH = "/api/v1";
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ClientService serviceUnderTest = null;

    ClientInputDto dummyClient = null;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private MailSenderService mailSenderService;


    @BeforeEach
    void setUp() {
        this.dummyClient = ClientInputDto.of("Test Client", "Test Surname", "test@example.com", LocalDate.now());
        this.serviceUnderTest = new ClientService(clientRepository, modelMapper, objectMapper, mailSenderService);

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(modelMapper.map(this.dummyClient, Client.class));
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(modelMapper.map(this.dummyClient, Client.class)));


    }

    @Test
    @DisplayName("Should CREATE client with valid payload")
    void shouldCreateClient() {
        Assertions.assertDoesNotThrow(() -> serviceUnderTest.createClient(dummyClient));
    }

    @Test
    @DisplayName("Should DELETE client with valid payload")
    void shouldDeleteClient() {
        Assertions.assertDoesNotThrow(() -> serviceUnderTest.deleteClient(1L));
    }

    @Test
    @DisplayName("Should throw when DELETE non existent client")
    void shouldThrowBadRequestWhenDeletingNonExistentClient() {
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotFoundException.class, () -> serviceUnderTest.deleteClient(1L));
    }

    @Test
    @DisplayName("Should GET ALL clients")
    void shouldGetAllClients() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(modelMapper.map(this.dummyClient, Client.class));
        clientList.add(modelMapper.map(this.dummyClient, Client.class));
        
        Mockito.when(clientRepository.findAll()).thenReturn(clientList);
        Assertions.assertDoesNotThrow(() -> serviceUnderTest.getAllClients());
    }

    @Test
    @DisplayName("Should GET single client with valid ID")
    void shouldGetSingleClient() {
        Assertions.assertDoesNotThrow(() -> serviceUnderTest.getClientById(1L));
    }

    @Test
    @DisplayName("Should throw when GET non existent client")
    void shouldThrowBadRequestWhenFindingNonExistentClient() {
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotFoundException.class, () -> serviceUnderTest.getClientById(1L));
    }

    @Test
    @DisplayName("Should UPDATE client with valid payload")
    void shouldUpdateSingleClient() {
        Assertions.assertDoesNotThrow(() -> serviceUnderTest.updateClient(1L, this.dummyClient));
    }

    @Test
    @DisplayName("Should throw when UPDATE non existent client")
    void shouldThrowBadRequestWhenUpdatingNonExistentClient() {
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotFoundException.class, () -> serviceUnderTest.updateClient(1L, this.dummyClient));
    }
}
