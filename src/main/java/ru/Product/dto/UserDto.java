package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    @Schema(
            requiredMode = NOT_REQUIRED,
            description = "user's id",
            name = "id",
            type = "UUID")
    private UUID id;
    @Schema(
            requiredMode = NOT_REQUIRED,
            description = "user's name",
            name = "name",
            type = "string")
    private String name;
    @Schema(
            requiredMode = REQUIRED,
            description = "user's email",
            name = "email",
            type = "string",
            example = "example@gmail.com")
    private String email;
    @Schema(
            requiredMode = REQUIRED,
            description = "user's phone",
            name = "phone",
            type = "string",
            example = "89997776655")
    @Size(min=10, max=10)
    private String phone;
    @Schema(
            requiredMode = REQUIRED,
            description = "user's address",
            name = "address",
            type = "string")
    private String address;
    @Schema(
            requiredMode = REQUIRED,
            description = "user's password",
            name = "password",
            type = "string")
    private String password;
}
