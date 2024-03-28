package com.example.sess.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sess.models.Client;
import com.example.sess.services.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{requestId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long requestId) {
        Client client = clientService.getClientById(requestId);

        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<Page<Client>> searchClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = true) String lastName,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {

        Page<Client> clientPage;
        if (firstName != null) {
            clientPage = clientService.getClientByFullName(firstName, lastName, pageable);
        } else {
            clientPage = clientService.getClientByLastName(lastName, pageable);
        }
        if (!clientPage.isEmpty()) {
            return ResponseEntity.ok(clientPage);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * 
     * @PostMapping
     *              public ResponseEntity<?> createClient(@RequestBody Client
     *              client) {
     *              Client createdClient = clientService.createClient(client);
     * 
     *              // Build the redirect URL
     *              URI location =
     *              ServletUriComponentsBuilder.fromCurrentContextPath().path("/clients/{clientId}")
     *              .buildAndExpand(createdClient.getClientId()).toUri();
     * 
     *              // Return the location of the created client profile
     *              return ResponseEntity.created(location).build();
     *              }
     *              }
     * 
     */
}