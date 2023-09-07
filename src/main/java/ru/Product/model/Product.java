package ru.Product.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Table(name="product")
@Data
@Builder
@Getter
public class Product {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name = "description")
    @Setter
    private String description;

    @Column(name = "image")
    @Setter
    private String image;

    @Column(name = "price")
    @Setter
    private Integer price;

    @Column(name = "quantity")
    @Setter
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
