package ru.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.Product.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDto {
    private UUID id;
    private String status;
    private String userName;
    private LocalDate dateTime;
    private String address;
    private Integer totalPrice;
    private List<OrderedProductDto> orderedProducts;
}
