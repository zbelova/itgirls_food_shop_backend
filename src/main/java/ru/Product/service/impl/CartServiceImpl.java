package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.Product.dto.CartDto;
import ru.Product.dto.CartItemDto;
import ru.Product.model.Cart;
import ru.Product.model.Product;
import ru.Product.model.User;
import ru.Product.repository.CartRepository;
import ru.Product.repository.ProductRepository;
import ru.Product.repository.UserRepository;
import ru.Product.service.CartService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SessionScope
@Slf4j
@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    private Cart getCartOrCreate(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Optional<Cart> cartOptional = cartRepository.findById(user.getId());
        Cart cart;
        /*if (cartOptional.isEmpty()) {
            cart = createCart(user);
        } else {
            cart = cartOptional.get();
        }*/
        if (user.getCart() == null) {
            cart = createCart(user);
        } else {
            cart = user.getCart();
        }
        return cart;
    }

    @Override
    public CartDto getCart(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = null;
        if (user.getCart() == null) {
            throw new RuntimeException("Корзина не найдена");
        } else {
            cart = user.getCart();
        }
        return convertEntityToDto(cart);
    }

    @Override
    public CartDto addToCartDto(UUID userId, UUID productId, Integer quantity) {
        log.info("Добавление продукат id: {} в корзину пользователем: {}", userId, productId);
        Cart cart = getCartOrCreate(userId);
        Product product = productRepository.findById(productId).orElseThrow();
        int productInStock = product.getQuantity();
        cart.updateItem(product, quantity, productInStock);
        return convertEntityToDto(cartRepository.save(cart));
    }

    @Override
    public Cart clearCart(UUID userId) {
        Cart cart = getCartOrCreate(userId);
        cart.clear();
        return cartRepository.save(cart);
    }

    private Cart createCart(User user) {
        Cart cart = cartRepository.save(new Cart(user));
        log.info("Создана корзина id: {} пользователя: {}", cart.getId(), user.getId());
        return cart;
    }

    private CartDto convertEntityToDto(Cart cart) {
        Set<CartItemDto> cartItemDtoSet = cart.getCartItems()
                .stream()
                .map(cartItem -> CartItemDto.builder()
                        .cartId(cartItem.getId())
                        .productId(cartItem.getProduct().getId())
                        .quantity(cartItem.getQuantity())
                        .cost(cartItem.calculateCost())
                        .build()
                ).collect(Collectors.toSet());
        return CartDto.builder()
                .userId(cart.getUser().getId())
                .cartItems(cartItemDtoSet)
                .TotalCot(cart.calculateItemsCost())
                .build();
    }
}