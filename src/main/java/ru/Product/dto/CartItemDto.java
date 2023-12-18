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
    private UUID cartId;
    private UUID productId;
    private Integer quantity;
    private Integer cost;
}
