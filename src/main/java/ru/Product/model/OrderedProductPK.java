package ru.Product.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProductPK implements Serializable {
    private UUID orderId;
    private UUID productId;
}
