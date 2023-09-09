package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveDto {
    @Schema(requiredMode = REQUIRED, description = "Список id продуктов")
    private List<UUID> product;
    @Schema(requiredMode = REQUIRED, description = "Текущая дата")
    private LocalDate dateTime;
    @Schema(requiredMode = REQUIRED, description = "Цена должна высчитываться при сохранении")
    private BigDecimal totalPrice;
    @Schema(requiredMode = REQUIRED, description = "Статус заказа")
    private String status;
    @Schema(requiredMode = REQUIRED, description = "id пользователя")
    private UUID userId;
}
