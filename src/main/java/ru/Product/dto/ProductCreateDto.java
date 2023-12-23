package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {
    @Size(min = 3, max = 20)
    @NotBlank(message = "Необходимо указать имя продукта")
    @Schema(requiredMode = REQUIRED, description = "Название продукта")
    private String name;

    @NotBlank(message = "Необходимо указать имя категории продукта")
    @Schema(requiredMode = REQUIRED, description = "Название категории")
    private String categoryName;

    @NotBlank(message = "Необходимо указать описание продукта")
    @Schema(requiredMode = REQUIRED, description = "Описание продукта")
    private String description;

    @NotBlank(message = "Необходимо указать цену продукта")
    @Schema(requiredMode = REQUIRED, description = "Цена")
    private BigDecimal price;

    @NotBlank(message = "Необходимо указать количество продукта")
    @Schema(requiredMode = REQUIRED, description = "Количество")
    private Integer quantity;

    @Schema(requiredMode = NOT_REQUIRED, description = "Изображение")
    private String image;
}
