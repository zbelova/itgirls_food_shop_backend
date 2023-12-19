package ru.Product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column(name = "description")
    private String description;

}

