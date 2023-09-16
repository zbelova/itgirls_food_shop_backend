package ru.Product.service;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;
import ru.Product.model.Order;
import ru.Product.model.User;
import ru.Product.repository.UserRepository;
import ru.Product.service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void testFindById() {
        UUID id =  UUID.randomUUID();
        String name = "Daria";
        String email = "daria@gmail.com";
        String phone = "89996665544";
        String address = "Smolensk";
        String password = "PASSword8!";
        Set<Order> orders = new HashSet<>();

        User user = new User (id, name, email, phone, address, password, orders);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto userDto = userService.findById(id);
        verify(userRepository).findById(id);
        Assertions.assertEquals(userDto.getId(),user.getId());
        Assertions.assertEquals(userDto.getName(),user.getName());
        Assertions.assertEquals(userDto.getEmail(),user.getEmail());
        Assertions.assertEquals(userDto.getPhone(),user.getPhone());
        Assertions.assertEquals(userDto.getAddress(),user.getAddress());
        Assertions.assertEquals(userDto.getPassword(),user.getPassword());
    }


    @Test
    public void testFindByIdNotFound() {
        UUID id =  UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(java.util.NoSuchElementException.class, () -> userService.findById(id));

        verify(userRepository).findById(id);
    }

    @Test
    public void testCreateUser() {
        UUID id =  UUID.randomUUID();
        String name = "Daria";
        String email = "daria@gmail.com";
        String phone = "89996665544";
        String address = "Smolensk";
        String password = "PASSword8!";
        Set<Order> orders = new HashSet<>();

        User user = new User (id, name, email, phone, address, password, orders);

        when(userRepository.save(user)).thenReturn(user);

        UserDto userDto = userService.createUser(new UserDto(id, name, email, phone, address, password));

        verify(userRepository).save(user);
        Assertions.assertEquals(userDto.getId(),user.getId());
        Assertions.assertEquals(userDto.getName(),user.getName());
        Assertions.assertEquals(userDto.getEmail(),user.getEmail());
        Assertions.assertEquals(userDto.getPhone(),user.getPhone());
        Assertions.assertEquals(userDto.getAddress(),user.getAddress());
        Assertions.assertEquals(userDto.getPassword(),user.getPassword());
    }

    @Test
    public void testUpdateUser() {
        UUID id =  UUID.randomUUID();
        String name = "Daria";
        String email = "daria@yandex.ru";
        String phone = "8999666554";
        String address = "Smolensk";
        String password = "PASSword8!";
        Set<Order> orders = new HashSet<>();

        User user = new User (id, name, email, phone, address, password, orders);

        when(userRepository.findUserByName(name)).thenReturn(Optional.of(user));

        UserDto userDto = userService.updateUser(new UserUpdateDto(id, name, phone, address));

        verify(userRepository).findUserByName(name);
        Assertions.assertEquals(userDto.getId(),user.getId());
        Assertions.assertEquals(userDto.getName(),user.getName());
        Assertions.assertEquals(userDto.getPhone(),user.getPhone());
        Assertions.assertEquals(userDto.getAddress(),user.getAddress());
    }

}
