package ru.Product.service.impl;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.CategoryCreateDto;
import ru.Product.dto.CategoryDto;
import ru.Product.dto.CategoryUpdateDto;
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
        log.info("Поиск всех категорий");
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(this::convertToCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getOne(UUID categoryId) {
        log.info("Поиск категории с id: {}", categoryId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category foundCategory = category.get();
            log.info("Найдена категория: {}", foundCategory);
            return convertToCategoryDto(foundCategory);
        } else {
            log.error("Категория не найдена с id: {}", categoryId);
            throw new NotFoundException("Категория не найдена с id: " + categoryId);
        }
    }

    @Override
    public CategoryDto createCategory(CategoryCreateDto categoryCreateDto) {
        log.info("Создание новой категории: {}", categoryCreateDto);
        String name = categoryCreateDto.getName();
        Category optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory != null)
            throw new EntityExistsException("Category with name " + name + " already  exists");
        else {
            Category newCategory = convertToCategoryEntity(categoryCreateDto);
            Category savedCategory = categoryRepository.save(newCategory);
            log.info("Категория создана: {}", savedCategory);
            return convertToCategoryDto(savedCategory);
        }
    }

    @Override
    public CategoryDto updateCategory(UUID id, CategoryUpdateDto categoryUpdateDto) {
        log.info("Обновление информации о категории с id: {}", id);
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        String newNameCategory = categoryUpdateDto.getName();
        Category foundCategory = categoryRepository.findByName(newNameCategory);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            if (foundCategory != null)
                throw new EntityExistsException("Category with name " + newNameCategory + " already  exists");
            else {
                existingCategory.setName(categoryUpdateDto.getName());
                existingCategory.setImage(categoryUpdateDto.getImage());
                Category updatedCategory = categoryRepository.save(existingCategory);
                log.info("Название категории изменено: {}", updatedCategory);
                return convertToCategoryDto(updatedCategory);
            }
        } else {
            log.error("Категория не найдена с id: {}", id);
            throw new NotFoundException("Category not found with id: " + id);
        }
    }

    @Override
    public void deleteCategory(UUID id) {
        log.info("Удаление категории с id: {}", id);
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            if (category.getProductList().isEmpty()) {
                categoryRepository.deleteById(id);
                log.info("Категория удалена с id: {}", id);
            } else {
                log.error("Ошибка удаления категории с id: {}", id);
                throw new IllegalStateException("Категория с id " + id + " содержит продукты и не может быть удалена");
            }
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
