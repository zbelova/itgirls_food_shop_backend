package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdateDto {

    @Schema(
            requiredMode = REQUIRED,
            description = "user's id",
            name = "id",
            type = "UUID")
    private UUID id;

    @Schema(
            requiredMode = REQUIRED,
            description = "user's name",
            name = "name",
            type = "string")
    private String name;

    @Schema(
            requiredMode = NOT_REQUIRED,
            description = "user's phone",
            name = "phone",
            type = "string",
            example = "89997776655")
    @Size(min=10, max=10)
    private String phone;
    @Schema(
            requiredMode = NOT_REQUIRED,
            description = "user's address",
            name = "address",
            type = "string")
    private String address;
}
