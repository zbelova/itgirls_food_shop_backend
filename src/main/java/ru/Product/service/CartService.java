package ru.Product.service;

import ru.Product.dto.CartDto;

import java.util.UUID;

public interface CartService {
    CartDto getCart(UUID userId);

    void addProductToCart(UUID userId, UUID productId, Integer quantity);

    void removeProductFromCart(UUID userId, UUID productId, Integer quantity);

    void clearCart(UUID userId);
}
