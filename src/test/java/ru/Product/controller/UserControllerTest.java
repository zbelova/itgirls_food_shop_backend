package ru.Product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.Product.dto.UserDto;
import ru.Product.dto.UserUpdateDto;
import ru.Product.model.Order;
import ru.Product.model.User;
import ru.Product.repository.UserRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

    @Test
    public void testSignUp() throws Exception {

        UUID id =  UUID.randomUUID();
        String name = "Test";
        String email = "test@mail.ru";
        String phone = "9009009090";
        String address = "Test str.";
        String password = "123456";
        Set<Order> orders = new HashSet<>();

        User user =  new User (id, name, email, phone, address, password, orders);
        UserDto userDto = new UserDto (id, name, email, phone, address, password);

        when(userRepository.save(Mockito.any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/signUp")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userDto.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(userDto.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword()));

        verify(userRepository).save(Mockito.any());
    }

    @Test
    public void testLogin() throws Exception {
        String email = "test@mail.ru";
        String password = "123456";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {

    UUID id =  UUID.randomUUID();
    String name = "Test";
    String email = "test@mail.ru";
    String phone = "9009009090";
    String address = "Test str.";
    String password = "123456";
    Set<Order> orders = new HashSet<>();

    User user =  new User (id, name, email, phone, address, password, orders);
    UserUpdateDto userDto = new UserUpdateDto(id, name, phone, address);

    when(userRepository.findUserByName(name)).thenReturn(Optional.of(user));
    when(userRepository.save(Mockito.any())).thenReturn(user);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/updateUser")
                    .content(asJsonString(userDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userDto.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userDto.getPhone()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(userDto.getAddress()));

    verify(userRepository).findUserByName(name);
    verify(userRepository).save(Mockito.any());
}


    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/logout"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
