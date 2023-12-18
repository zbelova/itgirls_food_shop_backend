package ru.Product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    public Cart(User user) {
        this.user = user;
    }

    public CartItem updateItem(Product product, Integer quantity) {
        if(product==null) {
            return null;
        }
        if (quantity==null) {
            quantity=0;
        }
        CartItem updatedItem = null;
        if (quantity > 0) {
            CartItem existingItem = findItem(product.getId());
            if(existingItem==null) {
                CartItem newItem = new CartItem(this, product, quantity);
                cartItems.add(newItem);
                updatedItem = newItem;
            } else {
                existingItem.setQuantity(quantity);
                updatedItem = existingItem;
            }
        } else {
            removeItem(product.getId());
        }
        return updatedItem;
    }

    public boolean isItemsEmpty() {
        return cartItems.isEmpty();
    }

    private CartItem findItem(UUID id) {
        for (CartItem existingItem : cartItems) {
            if (existingItem.getProduct().getId() == id)
                return existingItem;
        }
        return null;
    }

    private void removeItem(UUID id) {
        cartItems.removeIf(item -> item.getProduct().getId() == id);
    }

    public Integer calculateItemsCost() {
        return cartItems.stream()
                .mapToInt(CartItem::calculateCost)
                .sum();
    }

    public void clear() {
        cartItems.clear();
    }
}
