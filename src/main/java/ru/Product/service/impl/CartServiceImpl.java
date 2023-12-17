package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.Product.dto.*;
import ru.Product.model.Cart;
import ru.Product.model.Product;
import ru.Product.model.User;
import ru.Product.repository.CartRepository;
import ru.Product.repository.ProductRepository;
import ru.Product.repository.UserRepository;
import ru.Product.service.CartService;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SessionScope
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public CartDto getCartDtoOrCreate(UserDto userDto) {
        // не могу использовать UserService, тк метод getUserByEmail возвращает UserDto,
        // у которого не дб id, а мне нужно дальше проверить существует ли Cart с соответствующим User.id.
        // Если получить сначата userDto, а потом конвертировать его через convertDtoToEntity(),
        // то метод convertDtoToEntity() присвоет рандомный новый User.id, тк он создает нового User на основе UserDto
        User user = userRepository.findByEmail(userDto.getEmail());
        Optional <Cart> cartOptional = cartRepository.findById(user.getId());
        CartDto cartDto;
        if(cartOptional.isEmpty()) {
            cartDto = convertedEntityToDto(createCart(user));
        } else {
            cartDto = convertedEntityToDto(cartOptional.get());
        }
        return cartDto;
    }

    public Cart getCartOrCreate(UUID userId) {
        // не используется UserService, тк метод getUserByEmail возвращает UserDto,
        // у которого не дб id, а мне нужно дальше проверить существует ли Cart с соответствующим User.id.
        // Если получить сначата userDto, а потом конвертировать его через convertDtoToEntity(),
        // то метод convertDtoToEntity() присвоит рандомный новый User.id, тк он создает нового User на основе UserDto
        User user = userRepository.findById(userId).orElseThrow();
        Optional <Cart> cartOptional = cartRepository.findById(user.getId());
        Cart cart;
        if(cartOptional.isEmpty()) {
            cart = createCart(user);
        } else {
            cart = cartOptional.get();
        }
        return cart;
    }

    @Override
    public CartDto addToCartDto(UUID userId, UUID productId, Integer quantity) {
        Cart cart = getCartOrCreate(userId);
        // не используется ProductService, тк он возвращает ProductDto
        Product product = productRepository.findById(productId).orElseThrow();
        // проверка количества Product в репозитории (хранилище)
        int productCount = (int) productRepository.findById(productId).stream().count();
        if (productCount > 0) {
            cart.updateItem(product, quantity);
            return convertedEntityToDto(cartRepository.save(cart));
        } else {
            return convertedEntityToDto(cart);
        }
    }

    @Override
    public Cart addToCart(UUID userId, UUID productId, Integer quantity) {
        Cart cart = getCartOrCreate(userId);
        // не используется ProductService, тк он возвращает ProductDto
        Product product = productRepository.findById(productId).orElseThrow();
        // проверка количества Product в репозитории (хранилище)
        int productCount = (int) productRepository.findById(productId).stream().count();
        if (productCount > 0) {
            cart.updateItem(product, quantity);
            return cartRepository.save(cart);
        } else {
            return cart;
        }
    }

    @Override
    public CartDto clearCartDto(UUID userId) {
        Cart cart = getCartOrCreate(userId);
        cart.clear();
        return convertedEntityToDto(cartRepository.save(cart));
    }

    @Override
    public Cart clearCart(UUID userId) {
        Cart cart = getCartOrCreate(userId);
        cart.clear();
        return cartRepository.save(cart);
    }

    private Cart createCart(User user) {
         return cartRepository.save(new Cart(user));

    }

    private CartDto convertedEntityToDto(Cart cart) {
        Set<CartItemDto> cartItemDtoSet = cart.getCartItems()
                .stream()
                .map(cartItem -> CartItemDto.builder()
                        .cartId(cartItem.getId())
                        .productId(cartItem.getProduct().getId())
                        .quantity(cartItem.getQuantity())
                        .totalCost(cartItem.calculateCost())
                        .build()
                ).collect(Collectors.toSet());
        return CartDto.builder()
                .userId(cart.getUser().getId())
                .cartItems(cartItemDtoSet)
                .build();
    }
}

