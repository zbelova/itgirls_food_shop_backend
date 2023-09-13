package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.SignInDto;
import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;
import ru.Product.model.User;
import ru.Product.repository.UserRepository;
import ru.Product.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "Пользователи")
@RequiredArgsConstructor
public class UserController {

    //TODO UserService
    private final UserService userService;

    @Operation(summary = "signUp(User user) - регистрация. Сейчас используется простая регистрация через почтовый ящик, подтверждения адреса почты нет")
    @PostMapping("/api/v1/signUp")
    UserDto signUp(@RequestBody UserDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }


    @Operation(summary = "login(String email, String password) - авторизация")
    @GetMapping("/api/v1/login")
    UserDto login(@RequestParam(name = "email") String email, @RequestParam String password) {
        //return userService.signIn(signInDto);
        return userService.getUserByEmail(email);
    }




    @Operation(summary = "   updateUser(User user) - редактировать данные пользователя. Почтовый ящик сейчас поменять нельзя, потому что подтвердить изменение нельзя через почту. Пароль тоже нельзя. Но можем сделать просто смену почтового адреса и пароля в форме")
    @PutMapping("/api/v1/updateUser")
    UserDto updateUser(@RequestBody UserUpdateDto userCreateDto) {
        return userService.updateUser(userCreateDto);
    }


    @Operation(summary = "logout() - разлогиниться")
    @GetMapping("/api/v1/logout")
    String getAuthorsView(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
}
    @Operation(summary = "getUser() - получить данные текущего авторизованного пользователя")
    @GetMapping("/api/v1/getUser/{id}")
    UserDto getUser(@PathVariable("id") UUID id) {
        //TODO UserService.findById
       return userService.findById(id);
}


}


//    @GetMapping("/get")
//    @Operation(summary = "Получение пользователя по id")
//    public User getUser(
//            @Parameter(description = "id пользователя", required = true) @RequestParam(value = "id") String id
//    ) {
//        return null;
//    }