package ru.Product.dto;

import jakarta.persistence.Id;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderStatusDto {
    private Integer statusId;
    private String statusName;
    private String description;
}
