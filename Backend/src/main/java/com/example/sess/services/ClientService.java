package com.example.sess.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sess.dao.ClientRepository;
import com.example.sess.dto.ClientUpdateRequest;
import com.example.sess.models.Client;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createdClient(Client client) {

        if (client == null || clientRepository.existsByFirstNameAndLastNameAndDateOfBirth(client.getFirstName(),
                client.getLastName(), client.getDateOfBirth())) {
            return null;
        }
        return clientRepository.save(client);

    }

    public Client getClientById(Long id) {

        Optional<Client> client = clientRepository.findByClientId(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            return null;
        }

    }

    public Page<Client> getClientByFullName(String firstName, String lastName, Pageable pageable) {
        return clientRepository.findbyFirstNameAndLastName(firstName, lastName, pageable);
    }

    public Page<Client> getClientByLastName(String lastName, Pageable pageable) {
        return clientRepository.findByLastName(lastName, pageable);
    }

    /**
     * More getter if need TODO:
     */

    public boolean deleteClientById(Long id) {

        Boolean isDeleted = false;
        Optional<Client> client = clientRepository.findByClientId(id);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
            isDeleted = true;
        }

        return isDeleted;
    }

    public Optional<Client> updateClient(Long clientId, ClientUpdateRequest updateRequest) {

        Optional<Client> existingClient = clientRepository.findByClientId(clientId);

        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setFirstName(updateRequest.getFirstName());
            client.setLastName(updateRequest.getLastName());
            client.setGender(updateRequest.getGender());
            client.setEthnicity(updateRequest.getEthnicity());
            client.setDateOfBirth(updateRequest.getDateOfBirth());
            client.setAddress(updateRequest.getAddress());
            client.setLanguage(updateRequest.getLanguage());
            client.setZipCode(updateRequest.getZipCode());
            client.setDistrict(updateRequest.getDistrict());
            client.setPhoneNumber(updateRequest.getPhoneNumber());
            client.setInfoUrl(updateRequest.getInfoUrl());
            client.setComment(updateRequest.getComment());

            clientRepository.save(client);
        }
        return existingClient;

    }
}
