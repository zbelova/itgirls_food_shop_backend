package ru.Product.service;

import ru.Product.dto.CategoryCreateDto;
import ru.Product.dto.CategoryDto;
import ru.Product.dto.CategoryUpdateDto;
import ru.Product.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto> getAll();

    CategoryDto getOne(UUID id);

    CategoryDto createCategory(CategoryCreateDto categoryCreateDto);

    CategoryDto updateCategory(UUID id, CategoryUpdateDto categoryUpdateDto);
}
