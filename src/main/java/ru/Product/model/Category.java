package ru.Product.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Entity
@Table(name="category")
@Data
@Builder
public class Category {
    @Id
    @Column(name="id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name="image")
    private String image;

}
