package com.recipes.recipe.Controller;

import com.recipes.recipe.DTO.UserDTO;
import com.recipes.recipe.Service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> store(@RequestBody @Valid UserDTO userDTO){
        return this.userService.store(userDTO);
    }
}