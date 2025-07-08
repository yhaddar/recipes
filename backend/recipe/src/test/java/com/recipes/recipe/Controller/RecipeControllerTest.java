package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.CategoryDTO.CategoryDTORequest;
import com.recipes.recipe.Service.UploadToS3.S3Service;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class RecipeControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("categories")
            .withUsername("yhaddar")
            .withPassword("3705");

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Mock
    private S3Service s3Service;

    @Test
    void shouldEstablishConnection() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    private MockMultipartFile uploadFile() {
        return new MockMultipartFile(
                "image",
                "image.png",
                "text/plain",
                "hello world".getBytes()
        );
    }

    private CategoryDTORequest categoryDTORequest() {

        MockMultipartFile uploadImage = uploadFile();

        CategoryDTORequest categoryDTORequest = new CategoryDTORequest();
        categoryDTORequest.setTitle("new category");
        categoryDTORequest.setImage(uploadImage);

        return categoryDTORequest;
    }

    @Test
    void store() {

        // given
//        CategoryDTORequest categoryDTORequest = categoryDTORequest();

        // for stock the data like new FormData in javascript
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();

        // for convertir image tp byteArrayRessource
        ByteArrayResource resource = new ByteArrayResource("hello world".getBytes()) {
            @Override
            public String getFilename() {
                return "image.png";
            }
        };

        // for headers of endpoint
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        multiValueMap.add("title", "new category");
        multiValueMap.add("image", resource);


        MockMultipartFile uploadFile = new MockMultipartFile(
                "image",
                "image.png",
                "text/plain",
                "hello mockmvc".getBytes()
        );

        when(this.s3Service.uploadFile(uploadFile, "some/path")).thenReturn("category.png");

        CategoryDTORequest categoryDTORequest = new CategoryDTORequest();
        categoryDTORequest.setTitle("new category");
        categoryDTORequest.setImage(uploadFile);

        // when
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/api/v1/recipe/add",
                HttpMethod.POST,
                new HttpEntity<>(resource),
                Void.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    @Disabled
    void update() {
    }

    @Test
    @Disabled
    void delete() {
    }

    @Test
    @Disabled
    void search() {
    }

    @Test
    @Disabled
    void show() {
    }
}