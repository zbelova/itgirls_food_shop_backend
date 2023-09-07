package ru.Product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

// TODO добавить связь с таблицей заказов, так же пользователь будет связан с корзиной
@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "\\d{10}", message = "Номер телефона должен содержать 10 цифр") // один из вариантов валидации в БД
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_order",
            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<Order> orders;

}
