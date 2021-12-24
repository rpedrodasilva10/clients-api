package br.api.controller;

import br.api.entity.Client;
import br.api.exception.custom.ClientNotFoundException;
import br.api.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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


@ExtendWith(SpringExtension.class)
@WebMvcTest
@ComponentScan(value = "br.api")
class ClientControllerTest {

    private final String API_BASE_PATH = "/api/v1";
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientService service;

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
}

