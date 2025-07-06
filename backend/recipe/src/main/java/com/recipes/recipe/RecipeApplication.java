package com.recipes.recipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class RecipeApplication {

	public static final Logger log = LoggerFactory.getLogger(RecipeApplication.class);
	public static void main(String[] args) {
		log.info("start the application");
		SpringApplication.run(RecipeApplication.class, args);
	}

}
