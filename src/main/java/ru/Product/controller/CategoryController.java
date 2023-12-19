package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.CategoryCreateDto;
import ru.Product.dto.CategoryDto;
import ru.Product.dto.CategoryUpdateDto;
import ru.Product.service.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "Категории")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить список всех категорий")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить категорию по id")
    public CategoryDto getCategory(
            @Parameter(description = "id категории", required = true) @RequestParam String id
    ) {
        return categoryService.getOne(UUID.fromString(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создание новой категории")
    public ResponseEntity  createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        try {
            CategoryDto createdCategory = categoryService.createCategory(categoryCreateDto);
            return ResponseEntity.ok(createdCategory);
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body("Такая категория уже существует");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Обновление категории")
    public ResponseEntity updateCategory(@RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {
        UUID categoryId = UUID.fromString(categoryUpdateDto.getId());
        try {
            CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryUpdateDto);
            return ResponseEntity.ok(updatedCategory);
        }
        catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body("Такая категория уже существует");
        }

    }


}
