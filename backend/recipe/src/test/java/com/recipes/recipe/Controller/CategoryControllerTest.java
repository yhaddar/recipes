package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.CategoryDTO.CategoryDTORequest;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryControllerTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>();
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldEstablishConnection() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void index() {

        ResponseEntity<?> response = this.testRestTemplate.exchange(
                "/api/category",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void store() {

        // new FormData()
        MultiValueMap<String, Object> response = new LinkedMultiValueMap<>();

        // for convertir image to bytes
        ByteArrayResource resource = new ByteArrayResource("hello world".getBytes()) {
            @Override
            public String getFilename() {
                return "image.png";
            }
        };

        String title = "new category";
        response.add("title", title);
        response.add("image", resource);

        ResponseEntity<Void> result = this.testRestTemplate.exchange(
                "/api/category/add",
                HttpMethod.POST,
                new HttpEntity<>(response),
                Void.class
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void delete() {

        Category category = new Category();
        category.setTitle("new category");
        category.setImage("image.png");

        Category id = this.categoryRepository.save(category);
        UUID idCategory = id.getId();

        ResponseEntity<Void> result = this.testRestTemplate.exchange(
                "/api/category/delete?id=" + idCategory,
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    void update() {

        Category category = new Category();
        category.setTitle("new category");
        category.setImage("image.png");

        Category idCategory = this.categoryRepository.save(category);

        CategoryDTORequest request = new CategoryDTORequest();
        request.setTitle("update category");

        ResponseEntity<?> result = this.testRestTemplate.exchange(
                "/api/category/update?id=" + idCategory.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(request),
                String.class
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @AfterEach
    void tearDown() {
        this.categoryRepository.deleteAll();
    }
}