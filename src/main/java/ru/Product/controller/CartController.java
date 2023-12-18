package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.CartDto;
import ru.Product.dto.ProductAndPriceDto;
import ru.Product.dto.ProductAndQuantityDto;
import ru.Product.dto.ProductDto;
import ru.Product.service.CartService;
import ru.Product.model.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name = "Корзина")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/addProductToCart")
    @Operation(summary = "Добавить продукт в корзину")
    public void addProductToCart(
            @Parameter(description = "ID пользователя", required = true) @RequestParam("userId") String userId,
            @Parameter(description = "ID продукта", required = true) @RequestParam("productId") String productId,
            @Parameter(description = "Количество продукта", required = true) @RequestParam("quantity") Integer quantity
    ) {
       cartService.addToCartDto(UUID.fromString(userId), UUID.fromString(productId), quantity);
    }

    @PostMapping("/removeProductFromCart")
    @Operation(summary = "Убрать продукт из корзины по id")
    public void removeProductFromCart(
            @Parameter(description = "id продукта", required = true) @RequestParam @Valid String id) {
//        cartService.removeProduct(UUID.fromString(id));
    }

    @GetMapping("/getCart")
    @Operation(summary = "Получить все продукты в корзине с общей ценой")
    public CartDto getCart(
            @Parameter(description = "ID пользователя", required = true) @RequestParam("userId") String userId) {
        return cartService.getCart(UUID.fromString(userId));
    }

    @PostMapping("/removeAllProductsFromCart")
    @Operation(summary = "Убрать все продукты из корзины")
    public void removeAllProductsFromCart() {
//        cartService.clear();
    }

    @GetMapping("/getProductsInStock")
    @Operation(summary = "По списку продуктов получить количество в наличии")
    public List<ProductAndQuantityDto> getProductsInStock() {
//        return cartService.getCartItemsQuantity();
        return null;
    }
}