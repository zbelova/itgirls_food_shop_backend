package ru.Product.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        HttpBasicConfigurer<HttpSecurity> httpSecurityHttpBasicConfigurer = httpSecurity.csrf().disable().
                authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/api/v1/category/getOne").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/v1/category/getAll").hasAnyRole("ADMIN", "USER")

                                .requestMatchers("/api/v1/product/getAllProducts").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/v1/product/getOneProduct").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/v1/product/getAllProductsFromOneCategory").hasAnyRole("ADMIN", "USER")

                                .requestMatchers("api/v1/user/logout").hasAnyRole("ADMIN", "USER")

                                .requestMatchers("/api/v1/order/getAllOrdersByUserId").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/v1/order/getOrderById").hasAnyRole("ADMIN", "USER")

                                .requestMatchers("api/v1/user/getUser").hasRole("ADMIN")

                                .requestMatchers("/api/v1/category/create").hasRole("ADMIN")
                                .requestMatchers("/api/v1/category/update").hasRole("ADMIN")
                                .requestMatchers("/api/v1/category/delete").hasRole("ADMIN")

                                .requestMatchers("/api/v1/product/create").hasRole("ADMIN")
                                .requestMatchers("/api/v1/product/update").hasRole("ADMIN")
                                .requestMatchers("/api/v1/product/delete").hasRole("ADMIN")

                                .requestMatchers("/api/v1/order/getAllOrders").hasRole("ADMIN")
                                .requestMatchers("/api/v1/order/getAllOrders").hasRole("ADMIN")
                                .requestMatchers("/api/v1/order/updateProductQuantityInOrder").hasRole("ADMIN")
                                .requestMatchers("/api/v1/order/addProductToOrder").hasRole("ADMIN")
                                .requestMatchers("/api/v1/order/removeProductFromOrder").hasRole("ADMIN")

                                .requestMatchers("api/name/orderStatus/{name}").hasRole("ADMIN")
                                .requestMatchers("api/name/orderStatus/getAll").hasRole("ADMIN")

                                .requestMatchers("/api/v1/cart/addProductToCart").hasRole("USER")
                                .requestMatchers("/api/v1/cart/removeProductFromCart").hasRole("USER")
                                .requestMatchers("/api/v1/cart/getCart").hasRole("USER")
                                .requestMatchers("/api/v1/cart/removeAllProductsFromCart").hasRole("USER")
                                .requestMatchers("/api/v1/cart/getProductsInStock").hasRole("USER")

                                .requestMatchers("/api/v1/order/saveOrder").hasRole("USER")
                                .requestMatchers("api/v1/user/updateUser").hasRole("USER")

                                .anyRequest().authenticated())

                .httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
