package com.example.sess.controller;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sess.models.Client;
import com.example.sess.services.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @GetMapping("/{requestId}")
    // public ResponseEntity<Client> getSingleClient(@RequestParam String param, ) {

    // }

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