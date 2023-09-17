package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Product.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);
    User findByEmail(String email);

    List<User> findByName(String name);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByName(String name);
}
