package com.example.sess.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class ClientUpdateRequest {

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    private String gender;

    private String ethnicity;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private String address;

    private String language;

    private String zipCode;

    private Integer district;

    private String phoneNumber;

    private String infoUrl;

    private String comment;

    // Constructors, Getters, and Setters

    public ClientUpdateRequest() {
    }

    public ClientUpdateRequest(String firstName, String lastName, String gender, String ethnicity,
            LocalDate dateOfBirth, String address, String language, String zipCode, Integer district,
            String phoneNumber, String infoUrl, String comment) {
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

    // Add getters and setters for all fields
    // For example:
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Repeat for other fields...

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return this.ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getDistrict() {
        return this.district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInfoUrl() {
        return this.infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
