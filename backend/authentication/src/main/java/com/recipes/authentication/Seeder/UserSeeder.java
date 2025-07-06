package com.recipes.authentication.Seeder;

import com.recipes.authentication.Model.User;
import com.recipes.authentication.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserSeeder.class);
    private final UserRepository userRepository;
    private List<User> users = new ArrayList<>();

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        User user = new User();

        for(int i = 0; i <= 20; i++){
            user.setFirst_name("first"+i);
            user.setLast_name("last"+i);
            user.setEmail("first"+i+"last"+i+"@gmail.com");
            user.setUsername("first"+i+"last"+i);
            user.setPassword("123456789");

            this.users.add(user);
        }

        try {
            this.userRepository.save(user);
            log.info("data created with success");
        }catch(Exception e){
            log.error("failed to created fake user{} ", e.getMessage());
        }

    }
}