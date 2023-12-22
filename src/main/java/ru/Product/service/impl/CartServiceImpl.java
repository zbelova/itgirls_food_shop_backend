package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.webjars.NotFoundException;
import ru.Product.dto.CartDto;
import ru.Product.dto.CartItemDto;
import ru.Product.model.Cart;
import ru.Product.model.CartItem;
import ru.Product.model.Product;
import ru.Product.model.User;
import ru.Product.repository.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;

    @Override
    public CartDto getCart(UUID userId) {
        log.info("Поиск корзины пользователя id: {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            log.error("Пользователь не найден с id: {}", userId);
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else {
            User user = userOptional.get();
            Cart cart = getCartOrCreate(userId);
            return convertEntityToDto(cart);
        }
    }

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
    public void addProductToCart(UUID userId, UUID productId, Integer quantity) {
        log.info("Добавление продукта id: {} в корзину пользователя id: {}", productId, userId);
        Cart cart = getCartOrCreate(userId);

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            log.error("Продукт не найден с id: {}", productId);
            throw new NotFoundException("Продукт с id " + productId + " не найден");
        } else {
            Product product = productOptional.get();
            int productInStock = product.getQuantity();

            CartItem cartItem = getCartItemOrCreate(cart, product);

            log.info("Изменение количества продукта в корзине");
            if (cartItem.getQuantity() + quantity <= productInStock) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartRepository.save(cart);
            }
        }
    }

    @Override
    public void removeProductFromCart(UUID userId, UUID productId, Integer quantity) {
        log.info("Удаление продукта id: {} из корзины пользователя id: {}", productId, userId);
        Cart cart = getCartOrCreate(userId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            log.error("Продукт не найден с id: {}", productId);
            throw new NotFoundException("Продукт с id " + productId + " не найден");
        } else {
            Product product = productOptional.get();
            Optional<CartItem> cartItemOptional = getCartItem(cart, product);
            if (cartItemOptional.isEmpty()) {
                log.error("Продукт в корзине не найден с id: {}", userId);
                throw new NotFoundException("Продукт с id " + userId + " не найден");
            } else {
                CartItem cartItem = cartItemOptional.get();
                if (cartItem.getQuantity() - quantity > 0) {
                    log.info("Уменьшение количества продукта в корзине");
                    cartItem.setQuantity(cartItem.getQuantity() - quantity);
                } else {
                    log.info("Удаление продукта из корзины");
                    cart.getCartItems().remove(cartItem);
                    cartItemRepository.deleteById(cartItem.getId());
                }
                cartRepository.save(cart);
            }
        }
    }

    @Override
    public void clearCart(UUID userId) {
        log.info("Очистка корзины пользователя: {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            log.error("Пользователь не найден с id: {}", userId);
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        } else {
            Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                log.error("Очистка корзины id: {}", cart.getId());
                cartItemRepository.deleteCartItemByCartId(cart.getId());
            }
        }
    }

    private Cart createCart(User user) {
        Cart cart = cartRepository.save(new Cart(user));
        log.info("Создана корзина id: {} пользователя: {}", cart.getId(), user.getId());
        return cart;
    }

    private Optional<CartItem> getCartItem(Cart cart, Product product) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId() == product.getId())
                .findFirst();
    }

    private CartItem getCartItemOrCreate(Cart cart, Product product) {
        Optional<CartItem> itemOptional = cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(product.getId())).findFirst();
        CartItem cartItem;
        if (itemOptional.isPresent()) {
            log.info("Продукт найден в корзине");
            cartItem = itemOptional.get();
        } else {
            log.info("Продукт не найден в корзине");
            cartItem = CartItem.builder().id(UUID.randomUUID()).cart(cart).product(product).quantity(0).build();
            log.info("Добавление продукта в корзину");
            cart.getCartItems().add(cartItem);
        }
        return cartItem;
    }

    private CartDto convertEntityToDto(Cart cart) {
        Set<CartItemDto> cartItemDtoSet = cart
                .getCartItems()
                .stream()
                .map(cartItem -> CartItemDto.builder()
                        .id(cartItem.getId())
                        .productId(cartItem.getProduct().getId())
                        .productName(cartItem.getProduct().getName())
                        .quantity(cartItem.getQuantity())
                        .cost(cartItem.calculateCost())
                        .build())
                .collect(Collectors.toSet());
        return CartDto.builder()
                .userId(cart.getUser().getId())
                .cartItems(cartItemDtoSet)
                .TotalCost(cart.calculateItemsCost())
                .build();
    }
}