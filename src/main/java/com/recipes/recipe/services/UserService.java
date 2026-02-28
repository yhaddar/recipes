package com.recipes.recipe.services;

import com.recipes.recipe.models.Address;
import com.recipes.recipe.models.User;
import com.recipes.recipe.repositories.UserRepository;
import com.recipes.recipe.request.UserRequest;
import com.recipes.recipe.response.ResultResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Async
    @Transactional(rollbackOn = RuntimeException.class)
    public CompletableFuture<ResponseEntity<String>> register(@Valid UserRequest userRequest) {

        User user = new User();
        user.setFirst_name(userRequest.getFirstName());
        user.setLast_name(userRequest.getLastName());
        user.setBio(userRequest.getBio());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhone_whatsapp(userRequest.getPhoneWhatsapp());
        user.setGender(userRequest.getGender());

        Address address = new Address();

        address.setCity(userRequest.getAddress().getCity());
        address.setCountry(userRequest.getAddress().getCountry());
        address.setState(userRequest.getAddress().getState());
        address.setPostal_code(userRequest.getAddress().getPostalCode());

        user.setAddress(address);

        this.userRepository.save(user);

        return CompletableFuture.completedFuture(ResultResponse.success("your account was created", HttpStatus.CREATED));
    }
}
