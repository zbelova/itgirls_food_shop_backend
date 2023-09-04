package ru.Product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;

}
