package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.CartDto;
import ru.Product.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name = "Корзина")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/getCart")
    @Operation(summary = "Получить все продукты в корзине с общей ценой")
    public CartDto getCart(
            @Parameter(description = "ID пользователя", required = true) @RequestParam("userId") String userId) {
        return cartService.getCart(UUID.fromString(userId));
    }

    @PostMapping("/addProductToCart")
    @Operation(summary = "Добавить продукт в корзину")
    public void addProductToCart(
            @Parameter(description = "ID пользователя", required = true) @RequestParam("userId") String userId,
            @Parameter(description = "ID продукта", required = true) @RequestParam("productId") String productId
    ) {
        cartService.addProductToCart(UUID.fromString(userId), UUID.fromString(productId), 1);
    }

    @PostMapping("/removeProductFromCart")
    @Operation(summary = "Убрать продукт из корзины")
    public void removeProductFromCart(
            @Parameter(description = "ID пользователя", required = true) @RequestParam("userId") String userId,
            @Parameter(description = "ID продукта", required = true) @RequestParam("productId") String productId
    ) {
        cartService.removeProductFromCart(UUID.fromString(userId), UUID.fromString(productId), 1);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Очистить корзину")
    public void clearCart(@Parameter(description = "ID пользователя", required = true) @RequestParam("userId") String userId) {
        cartService.clearCart(UUID.fromString(userId));
    }
}