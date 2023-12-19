package ru.Product.service;

import ru.Product.dto.CartDto;
import ru.Product.model.Cart;

import java.util.UUID;

public interface CartService {
    CartDto getCart(UUID userId);

    CartDto addToCartDto(UUID userId, UUID productId, Integer quantity);

    Cart clearCart(UUID userId);
}
