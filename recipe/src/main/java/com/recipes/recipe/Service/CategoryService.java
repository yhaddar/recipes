package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.CategoryDTO.CategoryDTORequest;
import com.recipes.recipe.DTO.CategoryDTO.CategoryDTOResponse;
import com.recipes.recipe.Model.Entity.Category;
import com.recipes.recipe.Repository.CategoryRepository;
import com.recipes.recipe.Service.UploadToS3.S3Service;
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
    @Autowired
    private S3Service s3Service;
    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    public ResponseEntity<?> index(){

        try {

            List<Category> categories = this.categoryRepository.findAll();

            List<CategoryDTOResponse> categoryDTOResponse = categories.stream().map(CategoryDTOResponse::entityToJson).toList();

            if(!categoryDTOResponse.isEmpty())

                return ResponseEntity.ok().body(categoryDTOResponse);

            else
                throw new EntityNotFoundException("new categories will be added soon.");

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    public ResponseEntity<String> store(CategoryDTORequest categoryDTORequest) {
        try {

            MultipartFile cover = categoryDTORequest.getImage();

            String file_name = this.s3Service.uploadFile(cover, "categories");

            Category category = new Category();
            category.setImage(file_name);
            category.setTitle(categoryDTORequest.getTitle());

            try {
                this.categoryRepository.save(category);
                log.info("the category {} wa created", categoryDTORequest.getTitle());
                return ResponseEntity.status(HttpStatus.CREATED).body("the category was created");

            }catch (DataIntegrityViolationException e){
                log.error("failed to create category with title {}", categoryDTORequest.getTitle());
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }catch(Exception e){
            log.error("error : {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> delete(UUID id) {

        try {

            try{

                try {

                    Category category = this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));

                    this.categoryRepository.delete(category);

                    log.info("the category with Id {} was removed", id);
                    return ResponseEntity.noContent().build();

                }catch(DataIntegrityViolationException e){

                    log.error("This category by Id {} cannot be deleted because it is related to several recipes.", id);
                    return ResponseEntity.badRequest().body("This category cannot be deleted because it is related to several recipes.");

                }

            }catch (RuntimeException e){
                log.error("category not found with Id : {}", id);
                return ResponseEntity.notFound().build();
            }

        }catch(Exception e){
            log.error("failed to remove this category with Id : {}", id);
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<String> update(@Valid CategoryDTORequest categoryDTORequest, UUID id) {
        return null;
    }
}