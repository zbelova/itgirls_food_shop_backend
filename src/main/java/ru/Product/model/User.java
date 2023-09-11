package ru.Product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

// TODO добавить связь с таблицей заказов, так же пользователь будет связан с корзиной


@Entity
@Table(name="\"user\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;


        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "cart_id", referencedColumnName = "id")
        private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }




}
