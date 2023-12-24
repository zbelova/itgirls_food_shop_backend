package ru.Product.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = CartItem.class,
            mappedBy = "cart")
    private Set<CartItem> cartItems = new HashSet<>();

    public Cart(User user) {
        this.user = user;
    }

    public Integer calculateItemsCost() {
        return cartItems.stream()
                .mapToInt(CartItem::calculateCost)
                .sum();
    }

//    public CartItem updateItem(Product product, Integer quantity, int quantityLimit) {
//        log.info("Изменение количества продукта в корзине");
//        if (product == null) {
//            return null;
//        }
//        if (quantity == null) {
//            quantity = 0;
//        }
//        CartItem item = findItem(product.getId());
//        if (item == null) {
//            item = new CartItem(this, product, 0);
//            cartItems.add(item);
//        }
//        int quantityInCart = item.getQuantity();
//        if (quantityInCart + quantity <= 0) {
//            removeItem(product.getId());
//        } else if (quantityInCart + quantity <= quantityLimit) {
//            item.setQuantity(quantityInCart + quantity);
//        } else {
//            log.info("Невозможно добавить продукт в корзину. Количество ограничено: {}", quantityLimit);
//        }
//        return item;
//    }

//    public boolean isItemsEmpty() {
//        return cartItems.isEmpty();
//    }

//    public CartItem findItem(UUID productId) {
//        log.info("Поиск подукта id: {} в корзине", productId);
//        for (CartItem existingItem : cartItems) {
//            if (existingItem.getProduct().getId() == productId)
//                return existingItem;
//        }
//        return null;
//    }

//    private void removeItem(UUID productId) {
//        log.info("Удаление продукта id: {} из корзины", id);
//        log.info("Количество позиций до удаления: {}", cartItems.size());
//        for (CartItem existingItem : cartItems) {
//            if (existingItem.getProduct().getId() == productId) {
//                log.info("Продукт найден");
//                cartItems.remove(existingItem);
//                //existingItem.setCart(null);
//                log.info("Количество позиций после удаления: {}", cartItems.size());
//            }
//        }
//    }
}
