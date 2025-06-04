package com.recipes.recipe.Seeders;

import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


//@Component
public class CategorySeeder implements CommandLineRunner {

//    @Autowired
    private CategoryRepository categoryRepository;

    private final Logger log = LoggerFactory.getLogger(CategorySeeder.class);
    @Override
    public void run(String... args) throws Exception {

        Category category = new Category();
        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("sweet");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("lunch");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("Meat");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("breakfast");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("chicken");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("dessert");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("snack");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("chocolate");

        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("noodles");


        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("healthy");


        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("seafood");


        category.setImage("categories/1748883401528_sweets.png");
        category.setTitle("vegan");
        this.categoryRepository.save(category);


        log.info("the categories was affected");

    }
}