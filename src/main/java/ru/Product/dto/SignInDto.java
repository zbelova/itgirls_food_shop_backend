package ru.Product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInDto {
    @Schema(
            description = "user's email",
            name = "email",
            type = "string",
            example = "example@gmail.com")
    @NotNull
    @NotEmpty
    private String email;

    @Schema(
            description = "password to sign in",
            name = "password",
            type = "string",
            example = "password")
    @NotNull
    @NotEmpty
    private String password;
}
