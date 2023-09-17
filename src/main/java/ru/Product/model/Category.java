package ru.Product.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name="category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @Column(name="id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name="image")
    private String image;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}
