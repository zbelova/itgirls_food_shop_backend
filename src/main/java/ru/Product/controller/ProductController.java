package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.Product.dto.ProductDto;
import ru.Product.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = "Продукты")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAllProducts")
    @Operation(summary = "Получить список всех продуктов")
    public List<ProductDto> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/getOneProduct")
    @Operation(summary = "Получить продукт по id")
    public ProductDto getOneProduct(
            @Parameter(description = "id продукта", required = true) @RequestParam String id
    ) {
        return productService.getOne(UUID.fromString(id));
    }

    @GetMapping("/getAllProductsFromOneCategory")
    @Operation(summary = "Получить все продукты из одной категории")
    public List<ProductDto> getAllProductsFromOneCategory(
            @Parameter(description = "id категории", required = true) @RequestParam String id
    ) {
        return productService.getAllFromOneCategory(UUID.fromString(id));
    }

}
