package ru.Product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Product.dto.UserDto;
import ru.Product.model.User;
import ru.Product.repository.UserRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc//(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUser() throws Exception {

        UUID id =  UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .name("Test")
                .email("test@mail.ru")
                .phone("9009009090")
                .address("Test str.")
                .password("123456")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getUser")
                .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(user.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(user.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(user.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()));

        verify(userRepository).findById(id);
    }



}
