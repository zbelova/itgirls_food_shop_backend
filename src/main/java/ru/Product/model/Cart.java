package ru.Product.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@Component
@SessionScope

public class Cart {
    private final Map<String, Integer> cartItems = new HashMap<>(); // Используем айди продукта как ключ и количество как значение

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

    // Очистить корзину
    public void removeAllProductsFromCart() {
        cartItems.clear();
    }

    // Получить список всех продуктов в корзине с общей ценой
    public Map<String, Integer> getCart() {
        return cartItems;
    }
}
