package ru.Product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

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

  //  @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}",message = "Invalid email address")
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "\\d{10}", message = "Номер телефона должен содержать 10 цифр")
    private String phone;

    @Column(name = "address")
    private String address;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message = "Password is not secure")
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;


}
