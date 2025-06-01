package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.CategoryDTO;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    public ResponseEntity<?> index(){

        try {

            List<Category> categories = this.categoryRepository.findAll();

            if(!categories.isEmpty())
                return ResponseEntity.ok().body(categories);
            else
                throw new EntityNotFoundException("new categories will be added soon.");

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    public ResponseEntity<String> store(@Valid CategoryDTO categoryDTO) {
        try {

            MultipartFile cover = categoryDTO.getImage();

            String image_name = cover.getOriginalFilename();

            Category category = new Category();
            category.setImage(image_name);
            category.setTitle(categoryDTO.getTitle());

            try {

                this.categoryRepository.save(category);
                log.info("the category {} wa created", categoryDTO.getTitle());
                return ResponseEntity.status(HttpStatus.CREATED).body("the category was created");

            }catch (DataIntegrityViolationException e){
                log.error("failed to create category with title {}", categoryDTO.getTitle());
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }catch(Exception e){
            log.error("error : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(UUID id) {
        try {

            Category category = this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
            this.categoryRepository.delete(category);
            
            log.info("the category with Id {} was removed", id);
            return ResponseEntity.ok().body("this category was removed");

        }catch(Exception e){
            log.error("failed to remove this category with Id : {}", id);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> update(@Valid CategoryDTO categoryDTO, UUID id) {
        return null;
    }
}