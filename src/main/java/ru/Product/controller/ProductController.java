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

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = "Продукты")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/getAllProduct")
    @Operation(summary = "Получить список всех продуктов по категории")
    public List<ProductDto> getAllProduct(
            @Parameter(description = "id категории", required = true) @RequestParam String id
    ) {
        return null;
    }

    @GetMapping("/getOneProduct")
    @Operation(summary = "Получить продукт по id")
    public ProductDto getOneProduct(
            @Parameter(description = "id продукта", required = true) @RequestParam String id
    ) {
        return null;
    }

}
