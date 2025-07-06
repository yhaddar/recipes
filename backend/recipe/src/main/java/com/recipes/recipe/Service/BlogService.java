package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.BlogDTO.BlogDTORequest;
import com.recipes.recipe.DTO.BlogDTO.BlogDTOResponse;
import com.recipes.recipe.DTO.BlogDTO.BlogDTOUpdateRequest;
import com.recipes.recipe.DTO.BlogDTO.BlogShowDTOResponse;
import com.recipes.recipe.Model.Entity.Blog;
import com.recipes.recipe.Repository.BlogRepository;
//import com.recipes.recipe.Repository.UserRepository;
import com.recipes.recipe.Service.UploadToS3.S3Service;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogRepository blogRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    public ResponseEntity<?> index() {
        try {

            try {

                List<Blog> blogs = this.blogRepository.findAll();
                List<BlogDTOResponse> blogDTOResponses = blogs.stream().map(BlogDTOResponse::EntityToJson).toList();
                if(!blogDTOResponses.isEmpty()){

                    return ResponseEntity.ok().body(blogDTOResponses);

                }else
                    throw new RuntimeException("not blogs found");

            }catch(RuntimeException e){
                log.warn(e.getMessage());
                return ResponseEntity.noContent().build();
            }

        }catch(Exception e){
            log.error("failed to return all data of blogs, ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> store(@Valid BlogDTORequest blogDTORequest) {
        try{


            try {

//                User user = this.userRepository.findById(blogDTORequest.getUser()).orElseThrow(() -> new RuntimeException("no user found"));
//                MultipartFile image = blogDTORequest.getImage();
//                String file_name = this.s3Service.uploadFile(image, "blogs");
//
//                Blog blog = new Blog();
//                blog.setTitle(blogDTORequest.getTitle().toLowerCase());
//                blog.setDescription(blogDTORequest.getDescription().toLowerCase());
//                blog.setImage(file_name);
//                blog.setUser_id(user);

                try {
//                    this.blogRepository.save(blog);
                    log.info("this blog was created with title : {}", blogDTORequest.getTitle());
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CREATED).body("your blog was created"));

                }catch(DataIntegrityViolationException e){
                    log.warn(e.getMessage());
                    return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
                }

            }catch (RuntimeException e){
                log.warn(e.getMessage());
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
            }

        }catch(Exception e){
            log.error("Failed to add new blog, ", e);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
        }
    }

    public ResponseEntity<String> update(@Valid BlogDTOUpdateRequest blogDTOUpdateRequest, UUID id) {
        try {

            try {

                Blog blog = this.blogRepository.findById(id).orElseThrow(() -> new RuntimeException("no blog found"));

                String file_name = null;
                if(blogDTOUpdateRequest.getImage() != null){
                    MultipartFile image = blogDTOUpdateRequest.getImage();
                    file_name = this.s3Service.uploadFile(image, "blogs");
                    this.s3Service.deleteFile(blog.getImage());
                }

                if(blogDTOUpdateRequest.getTitle() != null) blog.setTitle(blogDTOUpdateRequest.getTitle());
                if(blogDTOUpdateRequest.getDescription() != null) blog.setDescription(blogDTOUpdateRequest.getDescription());
                if(blogDTOUpdateRequest.getImage() != null) blog.setImage(file_name);

                try {
                    this.blogRepository.save(blog);
                    log.info("this blog with Id : {} was updated", id);
                    return ResponseEntity.ok().body("this blog was updated");
                }catch(DataIntegrityViolationException e){
                    log.warn("", e);
                    return ResponseEntity.badRequest().body(e.getMessage());
                }

            }catch(RuntimeException e){
                log.warn(e.getMessage());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
            }

        }catch(Exception e){
            log.error("failed to update this recipe with Id : {}", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(UUID id) {
        try {

                try {
                    Blog blog = this.blogRepository.findById(id).orElseThrow(() -> new RuntimeException("no blog found"));

                    this.blogRepository.deleteById(blog.getId());
                    this.s3Service.deleteFile(blog.getImage());

                    return ResponseEntity.ok().body("this blog was removed");


                }catch(RuntimeException e){
                    log.warn(e.getMessage());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                }

        }catch(Exception e){
            log.error("failed to remove this blog with id : {}", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> show(UUID id) {
        try {

            try {
                Blog blog = this.blogRepository.findById(id).orElseThrow(() -> new RuntimeException("not blog found"));

                BlogShowDTOResponse blogShowDTOResponse = BlogShowDTOResponse.EntityToJson(blog);
                return ResponseEntity.ok().body(blogShowDTOResponse);

            }catch(RuntimeException e){
                log.warn(e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }

        }catch(Exception e){
            log.error("failed to found this blog with Id : {}", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> search(String q) {
        try {

           try {
               List<Blog> blogs = this.blogRepository.searchBlogWithTitle(q);

               List<BlogDTOResponse> blogDTOResponses = blogs.stream().map(BlogDTOResponse::EntityToJson).toList();
               if(!blogDTOResponses.isEmpty())
                   return ResponseEntity.ok().body(blogDTOResponses);
               else
                   throw new RuntimeException("no blog found");

           }catch(RuntimeException e){
               log.warn(e.getMessage());
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
           }

        }catch(Exception e){
            log.error("failed to search for this blog", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}