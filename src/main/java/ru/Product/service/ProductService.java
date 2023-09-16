package ru.Product.service;

import ru.Product.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getOne(UUID id);

}
