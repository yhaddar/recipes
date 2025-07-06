package com.recipes.recipe.Client;

import com.recipes.recipe.DTO.UserDTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://api-gateway:8080/api/v1/user")
public interface UserClient {
    @GetMapping("/recipe")
    UserDTO getUser(@RequestParam String user_id);

    @GetMapping("/verifier-user")
    Boolean verifierUser(@RequestParam String user_id);
}
