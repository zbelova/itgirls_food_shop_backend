package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;
import ru.Product.model.User;
import ru.Product.repository.UserRepository;
import ru.Product.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Try to create new user");
        User user = userRepository.save(convertDtoToEntity(userDto));
        userDto = convertEntityToDto(user);
        log.info("User: {}", userDto.toString());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        log.info("Try to update user");
        User user = userRepository.findUserByName(userUpdateDto.getName()).orElseThrow();
        user.setName(userUpdateDto.getName());
        user.setPhone(userUpdateDto.getPhone());
        user.setAddress(userUpdateDto.getAddress());
        User savedUser = userRepository.save(user);
        UserDto userDto = convertEntityToDto(savedUser);
        log.info("User: {}", userDto.toString());
        return userDto;
    }

    @Override
    public UserDto getUserByName(String name) {
        log.info("Try to get user by name");
        User user = userRepository.findUserByName(name).orElseThrow();
        UserDto userDto = convertEntityToDto(user);
        log.info("User: {}", userDto.toString());
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Try to get user by email");
        User user = userRepository.findUserByEmail(email).orElseThrow();
        UserDto userDto = convertEntityToDto(user);
        log.info("User: {}", userDto.toString());
        return userDto;
    }

    @Override
    public UserDto findById(UUID id) {
        log.info("Try to get user by id");
        User user = userRepository.findById(id).orElseThrow();
        UserDto userDto = convertEntityToDto(user);
        log.info("User: {}", userDto.toString());
        return userDto;
    }

    private UserDto convertEntityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .password(user.getPassword())
                .build();
    }

    private User convertDtoToEntity(UserDto userDto) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .address(userDto.getAddress())
                .password(userDto.getPassword())
                .build();
    }
}
