package ru.Product.service;

import ru.Product.model.Cart;
import ru.Product.model.Product;

import java.util.Map;

public interface CartService {
    void removeProduct(String productId);

    void addProduct(String productUUID);

    void clear();

    Map<Product, Integer> getCartItems(ProductService productService);

    void removeAllProductsFromCart(Cart cart);
}
