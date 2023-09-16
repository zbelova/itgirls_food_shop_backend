package ru.Product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Product.dto.UserDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc//(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUser() throws Exception {

        UUID id =  UUID.randomUUID();
        UserDto userDto = UserDto.builder()
                .id(id)
                .name("Test")
                .email("test@mail.ru")
                .phone("9009009090")
                .address("Test str.")
                .password("123456")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/getUser"))
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userDto.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(userDto.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword()));
    }



}
