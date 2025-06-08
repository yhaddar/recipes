package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.BlogDTO.BlogDTORequest;
import com.recipes.recipe.DTO.BlogDTO.BlogDTOUpdateRequest;
import com.recipes.recipe.Service.BlogService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public ResponseEntity<?> index(){
        return this.blogService.index();
    }

    @PostMapping("/add")
    public CompletableFuture<ResponseEntity<String>> store(@ModelAttribute @Valid BlogDTORequest blogDTORequest){
        return this.blogService.store(blogDTORequest);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@ModelAttribute @Valid BlogDTOUpdateRequest blogDTOUpdateRequest, @PathParam("id") UUID id){
        return this.blogService.update(blogDTOUpdateRequest, id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@PathParam("id") UUID id){
        return this.blogService.delete(id);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> show(@PathParam("id") UUID id){
        return this.blogService.show(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@PathParam("q") String q){
        return this.blogService.search(q);
    }
}