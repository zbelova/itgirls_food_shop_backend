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
import io.swagger.v3.oas.annotations.Operation;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "Пользователи")
@RequiredArgsConstructor
public class UserController {

    //TODO UserService
    private final UserService userService;

    @Operation(summary = "signUp(User user) - регистрация. Сейчас используется простая регистрация через почтовый ящик, подтверждения адреса почты нет")
    @PostMapping("/user/sign_up")
    UserDto signUp(@RequestBody UserDto userCreateDto) {
        //TODO UserService.createUser
        return userService.createUser(userCreateDto);
    }


    @Operation(summary = "login(String email, String password) - авторизация")
    @GetMapping("/user/sign_ip")
    UserDto login(@RequestBody SignInDto signInDto) {
        //TODO UserService.signIn
        return userService.signIn(signInDto);
    }




    @Operation(summary = "   updateUser(User user) - редактировать данные пользователя. Почтовый ящик сейчас поменять нельзя, потому что подтвердить изменение нельзя через почту. Пароль тоже нельзя. Но можем сделать просто смену почтового адреса и пароля в форме")
    @PutMapping("/user/updateUser")
    UserDto updateUser(@RequestBody UserUpdateDto userCreateDto) {
        //TODO UserService.updateUser
        return userService.updateUser(userCreateDto);
    }


    @Operation(summary = "logout() - разлогиниться")
    @GetMapping("/logout")
    String getAuthorsView(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
}
    @Operation(summary = "getUser() - получить данные текущего авторизованного пользователя")
    @GetMapping("/users/{id}")
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