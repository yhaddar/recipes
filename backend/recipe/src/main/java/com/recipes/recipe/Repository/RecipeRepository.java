package com.recipes.recipe.Repository;

import com.recipes.recipe.Model.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %:q%")
    List<Recipe> searchByTitle(@Param("q") String q);    

}