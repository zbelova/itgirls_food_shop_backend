package ru.Product.service;

import ru.Product.dto.*;
import ru.Product.model.Cart;
import java.util.UUID;

public interface CartService {

    CartDto getCartDtoOrCreate(UserDto userDto);

    Cart getCartOrCreate(UUID userId);

    CartDto addToCartDto(UUID userId, UUID productId, Integer quantity);

    Cart addToCart(UUID userId, UUID productId, Integer quantity);

    CartDto clearCartDto(UUID userId);

    Cart clearCart(UUID userId);
}
