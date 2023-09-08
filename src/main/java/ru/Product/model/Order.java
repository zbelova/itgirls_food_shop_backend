package ru.Product.model;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
@Entity
@Table(name="order")
@Data
@Builder
public class Order {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "product_id")
    @ElementCollection
    @CollectionTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<UUID> productIds;


    @Column(name = "date_time")
    private LocalDate dateTime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
