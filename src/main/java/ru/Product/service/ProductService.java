package ru.Product.service;

import ru.Product.dto.ProductCreateDto;
import ru.Product.dto.ProductDto;
import ru.Product.dto.ProductUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAll();

    ProductDto getOne(UUID productId);

    List<ProductDto> getAllFromOneCategory(UUID id);

    ProductDto createProduct(ProductCreateDto productCreateDto);

    ProductDto updateProduct(UUID productId, ProductUpdateDto productUpdateDto);

    void deleteProduct(UUID productId);

    Map<UUID, Integer> getProductsInStock(List<UUID> productIds);
}
