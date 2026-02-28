package com.recipes.recipe.controllers;

import com.recipes.recipe.request.UserRequest;
import com.recipes.recipe.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    CompletableFuture<ResponseEntity<String>> register(@RequestBody @Valid UserRequest userRequest){
        return this.userService.register(userRequest);
    }

}
