package com.example.sess.Client;

import org.hibernate.mapping.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.sess.controller.ClientController;
import com.example.sess.dao.ClientRepository;
import com.example.sess.dto.ClientUpdateRequest;
import com.example.sess.models.Client;
import com.example.sess.services.ClientService;

import io.jsonwebtoken.lang.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client testClient;
    private static Page<Client> testClientPage;

    @BeforeEach
    void setup() {
        testClient = new Client();
        testClient.setClientId(1L);
        testClient.setFirstName("John");
        testClient.setLastName("Doe");
        testClient.setGender("Male");
        testClient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testClient.setAddress("123 Main St");
        testClient.setLanguage("English");
        testClient.setZipCode("12345");
        testClient.setDistrict(1);
        testClient.setPhoneNumber("555-1234");
        testClient.setInfoUrl("http://example.com");
        testClient.setComment("Test comment");

        if (testClientPage == null) {
            testClientPage = createTestClientPage();
        }
    }

    public static Page<Client> createTestClientPage() {
        java.util.List<Client> clienList = new java.util.ArrayList<>();
        Client client1 = new Client("John", "Doe", "Male", "Caucasian", LocalDate.of(1967, 1, 1), "123 Main St",
                "English", "12345", 12, "415-555-1234", "http://example.com", null);
        Client client2 = new Client("Jane", "Doe", "Female", "Caucasian", LocalDate.of(1997, 2, 2), "124 Main St",
                "English", "12345", 21, "415-555-5678", "http://example.com", "happyman");
        Client client3 = new Client("Jane", "Doe", "Male", "Caucasian", LocalDate.of(1987, 2, 2), "124 Main St",
                "English", "54321", 33, "415-555-5678", "http://example.com", "sadman");
        client1.setClientId(2L);
        client2.setClientId(3L);
        client3.setClientId(4L);
        clienList.add(client1);
        clienList.add(client2);
        clienList.add(client3);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Client> clientPage = new PageImpl<>(clienList, pageRequest, clienList.size());
        return clientPage;

    }

    @Test
    void test_creatClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(testClient);

        Client createdClient = clientService.createdClient(testClient);

        assertThat(createdClient).isNotNull();
        verify(clientRepository).save(any(Client.class));

    }

    @Test
    void test_getClientById_ExistingClient() {

        Optional<Client> clientOptional = Optional.of(testClient);
        when(clientRepository.findByClientId(any(Long.class))).thenReturn(clientOptional);
        Client gClient = clientService.getClientById(testClient.getClientId());
        assertThat(gClient).isNotNull();
        assertEquals(clientOptional.get(), gClient);
        verify(clientRepository).findByClientId(any(Long.class));

    }

    @Test
    void test_getClientById_NonExistingClient() {

        Optional<Client> clientOptional = Optional.empty();
        when(clientRepository.findByClientId(1000L)).thenReturn(clientOptional);
        Client gNullClient = clientService.getClientById(1000L);
        assertThat(gNullClient).isNull();
        verify(clientRepository).findByClientId(any(Long.class));

    }

    @Test
    void test_getClientByLastName_Existingpage() {

        when(clientRepository.findByLastName(anyString(), any(Pageable.class))).thenReturn(testClientPage);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Client> clientPage = clientService.getClientByLastName("dummy", pageRequest);

        assertThat(clientPage).isNotEmpty();
        assertEquals(testClientPage, clientPage);
        verify(clientRepository).findByLastName(anyString(), any(Pageable.class));
    }

    @Test
    void test_getClientByLastName_NonExistingpage() {

        Page<Client> emptyPage = Page.empty();
        when(clientRepository.findByLastName(anyString(), any(Pageable.class))).thenReturn(emptyPage);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Client> clientPage = clientService.getClientByLastName("dummy", pageRequest);

        assertThat(clientPage).isEmpty();
        verify(clientRepository).findByLastName(anyString(), any(Pageable.class));
    }

    /**
     * More test for getClientBy{..} may added in the future;
     * Skip for now since all use same logic;
     */

    @Test
    public void test_deleteClientById_ExistClient() {
        Optional<Client> clientOptional = Optional.of(testClient);
        when(clientRepository.findByClientId(any(Long.class))).thenReturn(clientOptional);
        assertTrue(clientService.deleteClientById(testClient.getClientId()));
    }

    @Test
    public void test_deleteClientById_NonExistClient() {
        Optional<Client> clientOptional = Optional.empty();
        when(clientRepository.findByClientId(any(Long.class))).thenReturn(clientOptional);
        assertFalse(clientService.deleteClientById(testClient.getClientId()));
    }

    @Test
    public void test_updateClient_ExistingClient() {
        Optional<Client> clientOptional = Optional.of(testClient);
        when(clientRepository.findByClientId(any(Long.class))).thenReturn(clientOptional);
        ClientUpdateRequest updateRequest = new ClientUpdateRequest("UpdatedFirstName", "UpdatedLastName",
                "UpdatedGender",
                "UpdatedEthnicity",
                LocalDate.of(1990, 1, 1),
                "UpdatedAddress",
                "UpdatedLanguage",
                "UpdatedZipCode",
                1,
                "UpdatedPhoneNumber",
                "UpdatedInfoUrl",
                "UpdatedComment");

        Optional<Client> updatedClientOpt = clientService.updateClient(testClient.getClientId(), updateRequest);
        assertThat(updatedClientOpt).isPresent();
        Client updatedClient = updatedClientOpt.get();
        assertThat(updatedClient.getFirstName()).isEqualTo(updateRequest.getFirstName());
        assertThat(updatedClient.getLastName()).isEqualTo(updateRequest.getLastName());

        verify(clientRepository).findByClientId(testClient.getClientId());
        verify(clientRepository).save(any(Client.class));

    }

    @Test
    public void test_updateClient_NonExistingClient() {
        Optional<Client> clientOptional = Optional.empty();
        when(clientRepository.findByClientId(any(Long.class))).thenReturn(clientOptional);

        ClientUpdateRequest updateRequest = new ClientUpdateRequest("UpdatedFirstName", "UpdatedLastName",
                "UpdatedGender",
                "UpdatedEthnicity",
                LocalDate.of(1990, 1, 1),
                "UpdatedAddress",
                "UpdatedLanguage",
                "UpdatedZipCode",
                1,
                "UpdatedPhoneNumber",
                "UpdatedInfoUrl",
                "UpdatedComment");

        Optional<Client> updatedClientOpt = clientService.updateClient(testClient.getClientId(), updateRequest);
        assertThat(updatedClientOpt).isEmpty();

    }
}
