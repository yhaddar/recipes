package com.recipes.recipe.Repository;

import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Model.Entity.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("recipes")
            .withUsername("yhaddar")
            .withPassword("3705");
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    CategoryRepository categoryRepository;

    private Category categorySaved;

    @BeforeEach
    public void setup() {
        System.out.println("start the setup :");

        Category category = new Category();
        category.setTitle("category");
        category.setImage("image");
        this.categorySaved = this.categoryRepository.save(category);
    }

    @Test
    void canEstablishConnection(){
        System.out.println("TestContainer start.");
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void searchByTitle() {
        // Given

        Recipe recipe = new Recipe();
        recipe.setTitle("hello world from");
        recipe.setImage("image.png");
        recipe.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled i");
        recipe.setPrep_time(10);
        recipe.setCook_time(4);
        recipe.setUser("cbf15c27-0cf4-4a48-ae43-ab30b2b10879");
        recipe.setCategory(this.categorySaved);

        this.recipeRepository.save(recipe);

        // When
        Optional<List<Recipe>> recipeByTitle = Optional.ofNullable(this.recipeRepository.searchByTitle("youssef"));

        // Then
        assertThat(recipeByTitle).isPresent();

    }

    @AfterEach
    public void tearDown() {
        this.recipeRepository.deleteAll();
        this.categoryRepository.deleteAll();
        System.out.println("end the setup.");
    }

}