package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Product.model.Product;
import ru.Product.service.CartService;
import ru.Product.service.ProductService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ProductService {

    private final ProductService productService;

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

    // Добавить продукт в корзину
    public void addProductToCart(String productId) {
        cartItems.put(productId, cartItems.getOrDefault(productId, 0) + 1);
    }

    // Убрать продукт из корзины
    public void removeProductFromCart(String productId) {
        int itemCount = cartItems.getOrDefault(productId, 0);
        if (itemCount > 0) {
            cartItems.put(productId, itemCount - 1);
        }
    }

    // Добавить продукт в корзину
    public void addProductToCart(String productUUID) {
        cartItems.put(productUUID, cartItems.getOrDefault(productUUID, 0) + 1);
    }

    // Убрать продукт из корзины
    public void removeProductFromCart(String productUUID) {
        int itemCount = cartItems.getOrDefault(productUUID, 0);
        if (itemCount > 0) {
            cartItems.put(productUUID, itemCount - 1);

        }
    }

    // Очистить корзину
    public void removeAllProductsFromCart() {
        cartItems.clear();
    }

    // Получить список всех продуктов в корзине с общей ценой
    public Map<String, Integer> getCart() {
        return cartItems;
    }

    public Map<Product, Integer> getCartItems() {
        return getCartItems();
    }

    public void add(Product product) {
    }

}

