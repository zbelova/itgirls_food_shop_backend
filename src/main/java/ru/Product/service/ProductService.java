package ru.Product.service;

import ru.Product.dto.CategoryDto;
import ru.Product.dto.ProductCreateDto;
import ru.Product.dto.ProductDto;
import ru.Product.dto.ProductUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAll();

    ProductDto getOne(UUID id);

    List<ProductDto> getAllFromOneCategory(UUID id);

    ProductDto createProduct(ProductCreateDto productCreateDto);

    ProductDto updateProduct(UUID id, ProductUpdateDto productUpdateDto);

    void deleteProduct(UUID id);
}
