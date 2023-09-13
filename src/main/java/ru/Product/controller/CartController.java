package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Product.dto.CategoryDto;
import ru.Product.dto.ProductDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name = "Корзина")
@RequiredArgsConstructor
public class CartController {

    @PostMapping("/addProductToCart")
    @Operation(summary = "Добавить продукт в корзину по id")
    public ProductDto addProductToCart(
            @Parameter(description = "id продукта", required = true) @RequestParam String id
    ) {
        return null;
    }

    @PostMapping("/removeProductFromCart")
    @Operation(summary = "Убрать продукт из корзины по id")
    public void removeProductFromCart(
            @Parameter(description = "id продукта", required = true) @RequestParam String id
    ) {
    }

    @PostMapping("/removeAllProductsFromCart")
    @Operation(summary = "Убрать все продукты из корзины")
    public void removeAllProductsFromCart() {
    }

    @GetMapping("/getCart")
    @Operation(summary = "Получить все продукты в корзине с общей ценой по id пользователя")
    public List<ProductDto> getCart(
            @Parameter(description = "id пользователя", required = true) @RequestParam String id
    ) {
        return null;
    }

    // имеется ввиду поле Integer quantity в продуктах
    // лучше под этот метод сделать отдельную DTO: id, name и quantity
    // обрати внимание, на вход ждём List айди
    @GetMapping("/getProductsInStock")
    @Operation(summary = "По списку продуктов получить количество в наличии")
    public List<CategoryDto> getProductsInStock(
            @Parameter(description = "id продуктов", required = true) @RequestParam List<String> id
    ) {
        return null;
    }
}
