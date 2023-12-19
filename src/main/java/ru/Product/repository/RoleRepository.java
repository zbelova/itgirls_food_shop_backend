package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Product.model.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}

