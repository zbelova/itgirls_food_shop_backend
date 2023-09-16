package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Product.model.Order;
<<<<<<< HEAD
=======
import ru.Product.model.User;
>>>>>>> 9b8303b (0000)

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserId(UUID userId);

<<<<<<< HEAD
=======
    List<Order> findByUser(User user);
>>>>>>> 9b8303b (0000)
}
