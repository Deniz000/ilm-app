package com.ilim.app.dataAccess;

import com.ilim.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
    Optional<Category> findByName(String name);
}
