package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.Product.model.CartItem;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID>, JpaSpecificationExecutor<CartItem> {
}
