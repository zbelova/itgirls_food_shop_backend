package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Product.model.User;

@RestController
@RequestMapping("api/v1/user")
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
