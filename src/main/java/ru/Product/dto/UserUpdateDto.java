package ru.Product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdateDto {
    private UUID id;
    @NotNull
    @NotEmpty
    private String name;
//    @NotNull
//    @NotEmpty
//    private String email;
    @NotNull
    @NotEmpty
    @Size(min=10, max=10)
    private String phone;
    @NotNull
    @NotEmpty
    private String address;
//    @NotNull
//    @NotEmpty
//    private String password;
}
