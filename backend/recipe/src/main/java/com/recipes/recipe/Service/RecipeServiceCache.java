package com.recipes.recipe.Service;

import com.recipes.recipe.Client.UserClient;
import com.recipes.recipe.DTO.RecipeDTO.RecipeDTOResponse;
import com.recipes.recipe.DTO.RecipeDTO.RecipeShowDTOResponse;
import com.recipes.recipe.DTO.UserDTO.UserDTO;
import com.recipes.recipe.Model.Entity.Recipe;
import com.recipes.recipe.Repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecipeServiceCache {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserClient userClient;

    @Cacheable(value = "RECIPE_CACHE", key = "'all_recipes'")
    public List<RecipeDTOResponse> getRecipeFromCache(){

        List<Recipe> recipes = this.recipeRepository.findAll();
        return recipes.stream().map(
                recipe -> {
                    RecipeDTOResponse recipeDTOResponse = RecipeDTOResponse.EntityToJson(recipe);

                    UserDTO userDTO = this.userClient.getUser(recipe.getUser());
                    if(userDTO == null){
                        throw new NullPointerException("no user found");
                    }else {
                        recipeDTOResponse.setFirst_name(userDTO.getFirst_name());
                        recipeDTOResponse.setLast_name(userDTO.getLast_name());
                        recipeDTOResponse.setProfile(userDTO.getProfile());

                        return recipeDTOResponse;

                    }

                }
        ).toList();
    }

    @Cacheable(value = "RECIPE_CACHE", key = "'recipe_' +#id")
    public RecipeShowDTOResponse getRecipeDetailFromCache(UUID id){
        Recipe recipe = this.recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("recipe not found"));
        return RecipeShowDTOResponse.EntityToJson(recipe);
    }

    @Cacheable(value = "RECIPE_CACHE", key = "'recipe_search_'+ #q")
    public List<RecipeDTOResponse> getRecipeSearchFromCache(String q){
        List<Recipe> recipes = this.recipeRepository.searchByTitle(q);
        return recipes.stream().map(
                recipe -> {
                    RecipeDTOResponse recipeDTOResponse = RecipeDTOResponse.EntityToJson(recipe);

                    UserDTO userDTO = this.userClient.getUser(recipe.getUser());

                    recipeDTOResponse.setFirst_name(userDTO.getFirst_name());
                    recipeDTOResponse.setLast_name(userDTO.getLast_name());
                    recipeDTOResponse.setProfile(userDTO.getProfile());

                    return recipeDTOResponse;


                }
        ).toList();
    }
}
