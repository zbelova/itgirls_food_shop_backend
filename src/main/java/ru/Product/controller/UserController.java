package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;
import ru.Product.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "Пользователи")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Регистрация через почтовый ящик, подтверждения адреса почты нет")
    @PostMapping("/signUp")
    UserDto signUp(@RequestBody @Valid UserDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    //TODO реализация авторизации
    @Operation(summary = "Авторизация пользователя")
    @GetMapping("/login")
    UserDto login(@RequestParam("email") String email, @RequestParam String password) {
        return null;
    }

    @Operation(summary = "Редактируем данные пользователя (почтовый ящик и пароль поменять нельзя)")
    @PutMapping("/updateUser")
    UserDto updateUser(@RequestBody @Valid UserUpdateDto userCreateDto) {
        return userService.updateUser(userCreateDto);
    }

    @Operation(summary = "Выйти из аккаунта пользователя")
    @GetMapping("/logout")
    String logout(Model model) {
        return "redirect: /";
    }

    @Operation(summary = "Получить данные текущего авторизованного пользователя по его id")
    @GetMapping("/getUser")
    UserDto getUser(@RequestParam("id") UUID id) {
        return userService.findById(id);
    }

}