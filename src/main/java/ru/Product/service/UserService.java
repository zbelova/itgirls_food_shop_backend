package ru.Product.service;

import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;

import java.util.UUID;


public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    UserDto getUserByName(String name);

    UserDto getUserByEmail(String email);

    UserDto findById(UUID id);
}
