package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {
    @Schema(requiredMode = REQUIRED, description = "id категории")
    private String id;
    @Schema(requiredMode = REQUIRED, description = "Название категории")
    private String name;
    @Schema(requiredMode = NOT_REQUIRED, description = "Изображение")
    private String image;
}
