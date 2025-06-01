package com.recipes.recipe.Service;

import com.recipes.recipe.DTO.UserDTO;
import com.recipes.recipe.Enum.Role;
import com.recipes.recipe.Model.Entity.User;
import com.recipes.recipe.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<String> store(UserDTO userDTO){
        try {

            User user = new User();
            user.setFirst_name(userDTO.getFirst_name());
            user.setLast_name(userDTO.getLast_name());
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRole(userDTO.getRole() != Role.ADMIN ? Role.CLIENT : Role.ADMIN);

            try {

                this.userRepository.save(user);
                log.info("user with email {} was created", userDTO.getEmail());
                return ResponseEntity.accepted().body("your account was created with success");

            }catch (DataIntegrityViolationException e){
                return ResponseEntity.badRequest().body(e.toString());
            }

        }catch (Exception e){
            log.error("failed to create user with email {}, error : {} ", userDTO.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}