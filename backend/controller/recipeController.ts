import {Request, Response} from "express";
import Recipes from "../models/recipe";
import {RecipesDTO} from "../DTO/recipesDTO";
import Recipe from "../models/recipe";
import Category from "../models/category";
import User from "../models/user";

export class RecipeController {

    public async getAllRecipes(res: Response): Promise<any> {
        try {

            const AllRecipes: Recipes[] = await Recipes.findAll({
                include: [
                    {model: Category, attributes: ['category_title', 'category_image']},
                    {model: User, attributes: ['full_name', 'profile']}
                ]
            });
            return res.status(200).json({data: AllRecipes});

        } catch (err) {

            return res.status(500).json({error: (err as Error).message});
        }
    }

    public async setRecipe(req: Request, res: Response, file_name: string): Promise<any> {
        try {
            const recipesDTO = new RecipesDTO();
            recipesDTO.title = req.body.title;
            recipesDTO.description = req.body.description;
            recipesDTO.prep_time = req.body.prep_time;
            recipesDTO.cook_time = req.body.cook_time;
            recipesDTO.category_id = req.body.category_id;
            recipesDTO.user_id = req.body.user_id;
            recipesDTO.image = file_name;

            const save: Recipe = await Recipe.create({
                title: recipesDTO.title.toLowerCase(),
                image: recipesDTO.image,
                description: recipesDTO.description.toLowerCase(),
                prep_time: recipesDTO.prep_time,
                cook_time: recipesDTO.cook_time,
                user_id: recipesDTO.user_id,
                category_id: recipesDTO.category_id,
            });

            if (save)
                return res.status(200).json({data: "the recipe was created successfully."});
            else
                throw new Error("failed to creating recipe");

        } catch (err) {
            return res.status(500).json({error: (err as Error).message});
        }
    }
}