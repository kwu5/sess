package com.example.sess.Client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sess.controller.ClientController;
import com.example.sess.models.Client;
import com.example.sess.services.ClientService;
import com.example.sess.util.JwtUtil;

@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;
    @MockBean
    private JwtUtil jwtUtil;

    private Client mockClient;

    @BeforeEach
    void setup() {
        mockClient = new Client();
        mockClient.setClientId(1L);
        mockClient.setFirstName("John");
        mockClient.setLastName("Doe");
        mockClient.setGender("Male");
        mockClient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        mockClient.setAddress("123 Main St");
        mockClient.setLanguage("English");
        mockClient.setZipCode("12345");
        mockClient.setDistrict(1);
        mockClient.setPhoneNumber("555-1234");
        mockClient.setInfoUrl("http://example.com");
        mockClient.setComment("Test comment");

    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void getClientById_ShouldReturnAClient() throws Exception {

        when(clientService.getClientById(mockClient.getClientId())).thenReturn(mockClient);

        mockMvc.perform(get("/clients/{id}", mockClient.getClientId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(mockClient.getClientId()))
                .andExpect(jsonPath("$.firstName").value(mockClient.getFirstName()));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void getClientById_ShouldNotReturnANonExistClient() throws Exception {

        when(clientService.getClientById(mockClient.getClientId())).thenReturn(null);

        mockMvc.perform(get("/clients/{id}", mockClient.getClientId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void getClientByFullName_ShouldReturnAclient() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Client> clienList = new ArrayList();
        clienList.add(mockClient);
        Page<Client> clientPage = new PageImpl<>(clienList, pageRequest, 10);

        when(clientService.getClientByFullName(any(String.class), any(String.class), any(Pageable.class)))
                .thenReturn(clientPage);

        mockMvc.perform(get("/clients/search")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"));

        // TODO: add non exist test
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void getClientByLastName_ShouldReturnAclient() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Client> clienList = new ArrayList();
        clienList.add(mockClient);
        Page<Client> clientPage = new PageImpl<>(clienList, pageRequest, 10);

        when(clientService.getClientByLastName(any(String.class), any(Pageable.class)))
                .thenReturn(clientPage);

        mockMvc.perform(get("/clients/search")
                .param("lastName", "Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].firstName").value("John"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"));

        // TODO: add non exist test
    }
}
