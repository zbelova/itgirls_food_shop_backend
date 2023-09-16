package ru.Product.service.impl;

import org.springframework.stereotype.Service;
import ru.Product.model.Product;
import ru.Product.service.CartService;
import ru.Product.service.ProductService;

import java.util.Map;

@Service
public class CartServiceImpl implements ProductService {

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    // Добавить продукт в корзину по UUID
    public void addProductToCart(CartService cart, String productUUID) {
        cart.addProduct(productUUID);
    }

    // Убрать продукт из корзины по UUID
    public void removeProductFromCart() {
        removeProductFromCart(null, null);
    }

    // Убрать продукт из корзины по UUID
    public void removeProductFromCart(CartService cart, String productId) {
        cart.removeProduct(productId);
    }

    // Очистить корзину
    public void removeAllProductsFromCart(CartService cart) {
        cart.clear();
    }

    // Получить список продуктов в корзине с их количеством
    public Map<Product, Integer> getCartItems(CartService cart) {
        return cart.getCartItems(productService);
    }
}

