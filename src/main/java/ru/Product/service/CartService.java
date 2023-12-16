package ru.Product.service;

import ru.Product.dto.*;
import ru.Product.model.Cart;

public interface CartService {

    CartDto getCartDtoOrCreate(UserDto userDto);

    Cart getCartOrCreate(UserDto userDto);

    CartDto addToCartDto(CartItemDto cartItemDto);

    Cart addToCart(CartItemDto cartItemDto);

    CartDto clearCartDto(UserDto userDto);

    Cart clearCart(UserDto userDto);
}
