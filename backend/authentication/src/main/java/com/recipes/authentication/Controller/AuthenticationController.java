package com.recipes.authentication.Controller;

import com.recipes.authentication.DTO.UserDTOPostResponse;
import com.recipes.authentication.DTO.UserDTORequest;
import com.recipes.authentication.Model.User;
import com.recipes.authentication.Repository.UserRepository;
import com.recipes.authentication.Service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<String> store(@RequestBody @Valid UserDTORequest userDTO){
        return this.userService.store(userDTO);
    }

    @GetMapping
    public ResponseEntity<?> getUser(){

        List<User> users = this.userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/recipe")
    public ResponseEntity<?> userWithRecipe(@RequestParam UUID user_id){

        User user = this.userRepository.findUserById(user_id);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/verifier-user")
    public Boolean verifierUser(@RequestParam UUID user_id){
        return this.userService.verifierUser(user_id);
    }

}
