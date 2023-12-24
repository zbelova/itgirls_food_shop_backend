package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
    @Schema(requiredMode = REQUIRED, description = "id продукта")
    private UUID id;
    @Schema(requiredMode = REQUIRED, description = "Название продукта")
    private String name;
    @Schema(requiredMode = REQUIRED, description = "Название категории")
    private String categoryName;
    @Schema(requiredMode = REQUIRED, description = "Описание продукта")
    private String description;
    @Schema(requiredMode = REQUIRED, description = "Цена")
    private BigDecimal price;
    @Schema(requiredMode = REQUIRED, description = "Количество")
    private Integer quantity;
    @Schema(requiredMode = NOT_REQUIRED, description = "Изображение")
    private String image;
}
