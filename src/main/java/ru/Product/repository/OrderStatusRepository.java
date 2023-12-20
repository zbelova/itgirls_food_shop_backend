package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Product.model.OrderStatus;

import java.util.Optional;
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    Optional<OrderStatus> findByName(String statusName);


}
