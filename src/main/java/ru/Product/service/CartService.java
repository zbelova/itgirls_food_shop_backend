package ru.Product.service;

import ru.Product.dto.ProductAndPriceDto;
import ru.Product.dto.ProductAndQuantityDto;
import ru.Product.dto.ProductDto;
import ru.Product.model.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {
    void removeProduct(UUID id);

    void addProduct(UUID id);

    void clear();

    List<ProductAndPriceDto> getCartItemsPrice();
    List<ProductAndQuantityDto> getCartItemsQuantity();

}
