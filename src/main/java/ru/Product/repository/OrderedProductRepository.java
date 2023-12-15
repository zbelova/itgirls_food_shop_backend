package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Product.model.OrderedProduct;
import ru.Product.model.OrderedProductPK;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedProductPK> {
}
