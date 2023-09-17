package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.ProductCreateDto;
import ru.Product.dto.ProductDto;
import ru.Product.dto.ProductUpdateDto;
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

    @PostMapping("/create")
    @Operation(summary = "Создание нового продукта")
    public ProductDto createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        return productService.createProduct(productCreateDto);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновление продукта")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductUpdateDto productUpdateDto) {
        UUID productId = productUpdateDto.getId();
        ProductDto updatedProduct = productService.updateProduct(productId, productUpdateDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление продукта")
    public void deleteBook(@RequestBody ProductDto productDto) {
        UUID productId = productDto.getId();
        productService.deleteProduct(productId);
    }


}
