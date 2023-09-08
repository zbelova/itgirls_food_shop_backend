package ru.Product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import ru.Product.dto.UserDto;

@Controller
@RequiredArgsConstructor
public class RegistrationController {


//    login(String email, String password) - авторизация
//    signUp(User user) - регистрация. Сейчас используется простая регистрация через почтовый ящик, подтверждения адреса почты не

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    //    updateUser(User user) - редактировать данные пользователя. Почтовый ящик сейчас поменять нельзя, потому что подтвердить изменение нельзя через почту. Пароль тоже нельзя. Но можем сделать просто смену почтового адреса и пароля в форме


//    logout() - разлогиниться
//    getUser() - получить данные текущего авторизованного пользователя
}
