package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Product.model.Category;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
