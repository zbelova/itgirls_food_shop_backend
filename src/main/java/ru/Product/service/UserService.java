package ru.Product.service;

import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;


public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    UserDto getUserByName(String name);

    UserDto getUserByEmail(String email);
}
