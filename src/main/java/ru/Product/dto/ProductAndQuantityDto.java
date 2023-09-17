package ru.Product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductAndQuantityDto {
    private String name;
    private Integer quantity;

}
