package ru.Product.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.Product.model.Cart;
import ru.Product.model.CartItem;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> , JpaSpecificationExecutor<CartItem>{
    Long deleteByCart(Cart cart);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from cart_item where cart_id = ?")
    void deleteCartItemByCartId(UUID cart_id);
}
