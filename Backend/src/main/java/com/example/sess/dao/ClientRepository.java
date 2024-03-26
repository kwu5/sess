package com.example.sess.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sess.models.Client;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(Long clientId);

    Page<Client> findbyFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    Page<Client> findByLastName(String lastName, Pageable pageable);

    Page<Client> findByPhoneNumber(String phoneNumber, Pageable pageable);

    boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

}