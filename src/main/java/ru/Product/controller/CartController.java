package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Product.dto.ProductAndPriceDto;
import ru.Product.dto.ProductAndQuantityDto;
import ru.Product.dto.ProductDto;
import ru.Product.service.CartService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name = "Корзина")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @PostMapping("/addProductToCart")
    @Operation(summary = "Добавить продукт в корзину по id")
    public void addProductToCart(
            @Parameter(description = "id продукта", required = true) @RequestParam @Valid String id
    ) {
        cartService.addProduct(UUID.fromString(id));
    }

    @PostMapping("/removeProductFromCart")
    @Operation(summary = "Убрать продукт из корзины по id")
    public void removeProductFromCart(
            @Parameter(description = "id продукта", required = true) @RequestParam @Valid String id
    ) {
        cartService.removeProduct(UUID.fromString(id));
    }

    @PostMapping("/removeAllProductsFromCart")
    @Operation(summary = "Убрать все продукты из корзины")
    public void removeAllProductsFromCart() {
        cartService.clear();
    }

    @GetMapping("/getCart")
    @Operation(summary = "Получить все продукты в корзине с общей ценой")
    public List<ProductAndPriceDto> getCart() {
        return cartService.getCartItemsPrice();
    }

    @GetMapping("/getProductsInStock")
    @Operation(summary = "По списку продуктов получить количество в наличии")
    public List<ProductAndQuantityDto> getProductsInStock() {
        return cartService.getCartItemsQuantity();
    }

}
