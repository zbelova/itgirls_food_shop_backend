package ru.Product.service;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Product.dto.UserDto;
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
    public void testFindById () {
        UUID id =  UUID.randomUUID();
        String name = "Daria";
        String email = "daria@gmail.com";
        String phone = "89996665544";
        String address = "Smolensk";
        String password = "PASSword8!";
        Set<Order> orders = new HashSet<>();

        User user = new User (id, name, email, phone, address,password,orders);

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
}
