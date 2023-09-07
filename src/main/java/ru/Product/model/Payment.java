package ru.Product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;
@Entity
@Data
@Table(name = "payment")
@Builder
@Getter
public class Payment {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_id")
    @Setter
    private Integer orderId;

    @Column(name = "price")
    @Setter
    private Integer price;
}
