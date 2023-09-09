package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import ru.Product.model.User;

@SecurityRequirement(name = "library-users")
public class UserController {

    @GetMapping("/get")
    @Operation(summary = "Получение пользователя по id")
    public User getUser(
            @Parameter(description = "id пользователя", required = true) @RequestParam(value = "id") String id
    ) {
        return null;
    }


}
