package com.igor.bondezam.ToDoTask.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true, value = {"authorities"})
@Table(name = "user_task")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqe_user")
    @SequenceGenerator(allocationSize = 1, name = "sqe_user")
    private Long id;

    private String name;
    private Integer age;
    @Column(name = "birthdaydate")
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

    public User() {
    }

    public User(Long id, String name, Integer age, LocalDate birthdayDate, String cpf, String email, String password, Double height, Double weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthdayDate = birthdayDate;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.height = height;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
