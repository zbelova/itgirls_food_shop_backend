package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInDto {
    @Schema(
            requiredMode = REQUIRED,
            description = "user's email",
            name = "email",
            type = "string",
            example = "example@gmail.com")
    private String email;

    @Schema(
            requiredMode = REQUIRED,
            description = "password to sign in",
            name = "password",
            type = "string",
            example = "password")
    private String password;
}
