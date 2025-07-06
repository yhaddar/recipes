package com.recipes.authentication.Repository;

import com.recipes.authentication.DTO.UserDTOPostResponse;
import com.recipes.authentication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String string);

    User findUserById(UUID userId);
}