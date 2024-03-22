package com.example.sess.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_Id")
    private Long clientId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "ethnicity", nullable = false)
    private String ethnicity;

    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "district", nullable = false)
    private int district;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "folder_url", nullable = true)
    private String infoUrl;

    @Column(name = "comment", nullable = true)
    private String comment;

    public Client() {

    }

    public Client(String firstName, String lastName, String gender, String ethnicity,
            LocalDate dateOfBirth, String address, String language, String zipCode, int district, String phoneNumber,
            String infoUrl, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.language = language;
        this.zipCode = zipCode;
        this.district = district;
        this.phoneNumber = phoneNumber;
        this.infoUrl = infoUrl;
        this.comment = comment;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getEthnicity() {
        return this.ethnicity;
    }

    public String getAddress() {
        return this.address;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public int getDistrict() {
        return this.district;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getInfoUrl() {
        if (infoUrl == null)
            return "No url variable";
        return this.infoUrl;
    }

    public String getComment() {
        if (comment == null)
            return "No comment";
        return comment;

    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId) && Objects.equals(firstName, client.firstName)
                && Objects.equals(lastName, client.lastName) && Objects.equals(gender, client.gender)
                && Objects.equals(ethnicity, client.ethnicity) && Objects.equals(dateOfBirth, client.dateOfBirth)
                && Objects.equals(address, client.address) && Objects.equals(language, client.language)
                && Objects.equals(zipCode, client.zipCode) && district == client.district
                && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(infoUrl, client.infoUrl)
                && Objects.equals(comment, client.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, firstName, lastName, gender, ethnicity, dateOfBirth, address, language, zipCode,
                district, phoneNumber, infoUrl, comment);
    }

}
