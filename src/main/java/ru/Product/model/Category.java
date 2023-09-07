package ru.Product.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Table(name="category")
@Data
@Builder
@Getter
public class Category {
    @Id
    @Column(name="id")
    private UUID id;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name="image")
    @Setter
    private String image;

}
