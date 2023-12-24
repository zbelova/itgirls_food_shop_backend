package ru.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderedProductDto {
    private UUID productId;
    private String productName;
    private Integer productPrice;
    private Integer productQuantity;
}
