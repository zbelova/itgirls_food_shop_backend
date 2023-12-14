package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Product.model.Cart;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
