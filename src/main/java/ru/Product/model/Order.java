package ru.Product.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;
@Entity
@Table(name="order")
@Data
@Builder
@Getter
public class Order {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "products")
    @ManyToMany
    @JoinTable(
            name="product_order",
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
    )
    private List<Product> products;

    @Column(name = "date_time")
    @Setter
    private Timestamp dateTime;

    @Column(name = "total_price")
    @Setter
    private Integer totalPrice;

    @Column(name = "status")
    @Setter
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
