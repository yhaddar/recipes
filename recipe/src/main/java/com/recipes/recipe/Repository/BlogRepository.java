package com.recipes.recipe.Repository;

import com.recipes.recipe.Model.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {

    @Query("SELECT b FROM Blog b WHERE b.title LIKE %:q%")
    List<Blog> searchBlogWithTitle(@Param("q") String q);

}