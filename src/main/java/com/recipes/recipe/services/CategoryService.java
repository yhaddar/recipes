package com.recipes.recipe.services;

import com.recipes.recipe.config.S3Config;
import com.recipes.recipe.dto.CategoryDTO;
import com.recipes.recipe.exception.HandlerValidationException;
import com.recipes.recipe.exception.NotFoundException;
import com.recipes.recipe.models.Category;
import com.recipes.recipe.repositories.CategoryRepository;
import com.recipes.recipe.request.CategoryRequest;
import com.recipes.recipe.response.ResultResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final S3Config s3Config;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, S3Config s3Config) {
        this.categoryRepository = categoryRepository;
        this.s3Config = s3Config;
    }

    @Transactional(rollbackOn = NotFoundException.class)
    @Cacheable(value = "CATEGORY_SERVICE", key = "'category'")
    public List<CategoryDTO> index() throws NotFoundException {
        List<Category> category = this.categoryRepository.findAll();

        if(category.isEmpty()){
            throw new RuntimeException("no category found");
        }else {
            return category.stream().map(CategoryDTO::toJSON).toList();
        }
    }

    @Async
    @Transactional(rollbackOn = HandlerValidationException.class)
    @CacheEvict(value = "CATEGORY_SERVICE", key = "'category'")
    public CompletableFuture<ResponseEntity<String>> store(CategoryRequest categoryRequest) throws IOException {

        String file = categoryRequest.getImage().getOriginalFilename();
        String key = "categories/"+file;
        this.s3Config.uploadFile(key, categoryRequest.getImage().getInputStream());

        Category category = new Category();
        category.setTitle(categoryRequest.getTitle());
        category.setImage(this.s3Config.getUrl(key));
        this.categoryRepository.save(category);

        return CompletableFuture.completedFuture(ResultResponse.success("category was added with success", HttpStatus.CREATED));
    }

    @Transactional(rollbackOn = RuntimeException.class)
    @Async
    public CompletableFuture<ResponseEntity<String>> delete(UUID id) {

        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
        this.categoryRepository.deleteById(id);
        this.s3Config.deleteFile(category.getImage());
        return CompletableFuture.completedFuture(ResultResponse.success("category deleted", HttpStatus.NO_CONTENT));

    }

    @Transactional(rollbackOn = RuntimeException.class)
    @CacheEvict(value = "CATEGORY_SERVICE", key = "'category'")
    @Async
    public CompletableFuture<ResponseEntity<String>> update(UUID id, @Valid CategoryRequest categoryRequest) throws IOException {

        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));

        if(!categoryRequest.isImageNull()){
            String file = categoryRequest.getImage().getOriginalFilename();
            String key = "categories/"+file;
            this.s3Config.updateFile(key, categoryRequest.getImage().getInputStream(), category.getImage());
            category.setImage(this.s3Config.getUrl(key));
        }
        category.setTitle(categoryRequest.getTitle());
        this.categoryRepository.save(category);

        return CompletableFuture.completedFuture(ResultResponse.success("category updated", HttpStatus.OK));
    }
}
