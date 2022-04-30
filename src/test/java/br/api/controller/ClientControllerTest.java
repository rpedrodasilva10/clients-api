package br.api.controller;

import br.api.dto.ClientInputDto;
import br.api.entity.Client;
import br.api.exception.custom.ClientNotFoundException;
import br.api.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = ClientController.class)
@ComponentScan(value = "br.api")
class ClientControllerTest {

    private final String API_BASE_PATH = "/api/v1";
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ClientService service;

    ClientInputDto dummyClient = null;

    @BeforeEach
    void setUp() {
        this.dummyClient = ClientInputDto.of("Test Client", "Test Surname", "test@example.com", LocalDate.now());
    }

    @SneakyThrows
    @DisplayName("Should return 400, when missing required field")
    @ValueSource(strings = {"Name", "Surname", "Email", "BirthDate"})
    @ParameterizedTest(name = "{index} - {0}")
    void shouldReturnBadRequestForMissingRequiredFields(String field) {
        switch (field) {
            case "Name":
                this.dummyClient.setName(null);
                break;
            case "Surname":
                this.dummyClient.setSurname(null);
                break;
            case "Email":
                this.dummyClient.setEmail(null);
                break;
            case "BirthDate":
                this.dummyClient.setBirthDate(null);
                break;

            default:
                Assertions.fail("Field '" + field + "' not found'");
        }

        mockMvc.perform(MockMvcRequestBuilders
                .post(API_BASE_PATH + "/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.dummyClient))
        )
                .andExpect(status().isBadRequest()).andDo(print());
    }


    @Test
    void getAllClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1L, "Renan", "Silva", "example@example.com", LocalDate.of(2021, 1, 1), LocalDateTime.now(), LocalDateTime.now()));
        clients.add(new Client(2L, "Pri", "Silva", "example@example.com", LocalDate.of(2021, 1, 1), LocalDateTime.now(), LocalDateTime.now()));

        when(service.getAllClients()).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get(API_BASE_PATH + "/clients")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getSpecificClient() throws Exception {
        Long clientId = 10L;

        Client newClient = new Client(clientId, "Renan", "Silva", "example@example.com", LocalDate.of(2021, 1, 1), LocalDateTime.now(), LocalDateTime.now());
        when(service.getClientById(clientId)).thenReturn(newClient);

        mockMvc.perform(MockMvcRequestBuilders.get(API_BASE_PATH + "/clients/" + clientId).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", is(clientId.intValue())))
                .andDo(print());
    }

    @Test
    void getSpecificClientBadId() throws Exception {
        Long clientId = 9999L;

        when(service.getClientById(clientId)).thenThrow(new ClientNotFoundException(clientId));

        mockMvc.perform(MockMvcRequestBuilders.get(API_BASE_PATH + "/clients/" + clientId).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void createClient() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        ClientInputDto clientInputDTO = ClientInputDto.of("Renan Pedro", "Silva", "example@example.com", LocalDate.now());

        Client clientToBeCreated = modelMapper.map(clientInputDTO, Client.class);

        when(service.createClient(clientInputDTO)).thenReturn(clientToBeCreated);

        String objectAsJsonString = objectMapper.writeValueAsString(clientToBeCreated);

        mockMvc.perform(
                MockMvcRequestBuilders.post(API_BASE_PATH + "/clients").contentType(MediaType.APPLICATION_JSON).content(objectAsJsonString))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}

