package com.recipes.recipe.repositories;

import com.recipes.recipe.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface categoryRepository extends JpaRepository<Category, UUID> {
}
