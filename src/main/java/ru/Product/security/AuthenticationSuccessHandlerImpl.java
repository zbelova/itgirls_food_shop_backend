package ru.Product.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.Product.repository.UserRepository;

import java.io.IOException;
import java.util.Date;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

//    @Autowired
    private UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        userRepository.updateLastLogin(new Date());
    }
}
