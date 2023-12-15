package ru.Product.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_time", nullable = false)
    private LocalDate dateTime;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            targetEntity = OrderedProduct.class, mappedBy = "order")
    private Set<OrderedProduct> orderedProducts = new HashSet<>();
}
