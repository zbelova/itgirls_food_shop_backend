package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.Product.dto.ProductAndPriceDto;
import ru.Product.dto.ProductAndQuantityDto;
import ru.Product.model.Product;
import ru.Product.repository.ProductRepository;
import ru.Product.service.CartService;

import java.util.*;

@Component
@RequiredArgsConstructor
@SessionScope
@Slf4j
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    List<Optional<Product>> cart = new ArrayList<>();

    @Override
    public void removeProduct(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        cart.remove(product);
        log.info("Продукт с ID: {} удален", id);
    }

    @Override
    public void addProduct(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        cart.add(product);
        log.info("Продукт с ID: {} добавлен", id);
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public List<ProductAndPriceDto> getCartItemsPrice() {
        return null;
    }

    @Override
    public List<ProductAndQuantityDto> getCartItemsQuantity() {
        return null;
    }
}

