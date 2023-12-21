package ru.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemDto {
    private UUID id;
    private UUID productId;
    private String productName;
    private Integer quantity;
    private Integer cost;
}
