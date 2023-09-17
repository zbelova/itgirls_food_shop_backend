package ru.Product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductAndPriceDto {
    private String name;
    private BigDecimal price;


}
