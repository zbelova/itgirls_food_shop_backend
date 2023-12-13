package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.CategoryCreateDto;
import ru.Product.dto.CategoryDto;
import ru.Product.dto.CategoryUpdateDto;
import ru.Product.dto.ProductDto;
import ru.Product.model.Category;
import ru.Product.repository.CategoryRepository;
import ru.Product.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(this::convertToCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getOne(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category foundCategory = category.get();
            return convertToCategoryDto(foundCategory);
        } else {
            throw new NotFoundException("Category not found with id: " + id);
        }
    }

    @Override
    public CategoryDto createCategory(CategoryCreateDto categoryCreateDto) {
        Category newCategory = convertToCategoryEntity(categoryCreateDto);
        Category savedCategory = categoryRepository.save(newCategory);
        return convertToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(UUID id, CategoryUpdateDto categoryUpdateDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(categoryUpdateDto.getName());
            existingCategory.setImage(categoryUpdateDto.getImage());

            Category updatedCategory = categoryRepository.save(existingCategory);

            return convertToCategoryDto(updatedCategory);
        } else {
            throw new NotFoundException("Category not found with id: " + id);
        }
    }

    private CategoryDto convertToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .build();
    }

    private Category convertToCategoryEntity(CategoryCreateDto categoryCreateDto) {
        return Category.builder()
                .id(UUID.randomUUID())
                .name(categoryCreateDto.getName())
                .image(categoryCreateDto.getImage())
                .build();
    }
}
