package ru.Product.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.util.UUID;
@Entity
@Data
@Table(name = "payment")
@Builder
public class Payment {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price")
    private BigDecimal price;
}
