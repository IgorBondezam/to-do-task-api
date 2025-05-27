package com.igor.bondezam.ToDoTask.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Valid
public class UserReq {

    private String name;
    private LocalDate birthdayDate;
    @CPF
    @NotBlank
    private String cpf;
    @Email
    @NotBlank
    private String email;
    private String password;
    private Double height;
    private Double weight;

    public UserReq() {
    }

    public UserReq(String name, LocalDate birthdayDate, String cpf, String email, String password, Double height, Double weight) {
        this.name = name;
        this.birthdayDate = birthdayDate;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
