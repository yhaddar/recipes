import { Request, Response } from "express";
import {IngredientDTO} from "../DTO/ingredientDTO";
import Ingredient from "../models/Ingredient";
import {IngredientRepository} from "../Repository/IngredientRepository";

export class IngredientController {
    private ingredientRepository!: IngredientRepository;

    public constructor(){
        this.ingredientRepository = new IngredientRepository();
    }

    public async setIngredient(req: Request, res: Response): Promise<any> {
        try{

            const ingredientDTO: IngredientDTO = new IngredientDTO();
            ingredientDTO.title = req.body.title;
            ingredientDTO.description = req.body.description;
            ingredientDTO.recipe_id = req.body.recipe_id;

            const ingredient: Ingredient = await Ingredient.create({
                title: ingredientDTO.title,
                description: ingredientDTO.description,
                recipe_id: req.body.recipe_id,
            });

            if (ingredient)
                return res.status(201).json({ message: "ingredient created" });
            else
                throw new Error("failed to create ingredient");

        }catch(err){
            return res.status(500).json({ error: (err as Error).message });
        }
    }

    public async getIngredient(req: Request, res: Response): Promise<any> {
        try{

            const { recipe_id } = req.query;
            const [ingredients] = await this.ingredientRepository.findAllIngredient(recipe_id);

            return res.status(200).json({ data: ingredients });

        }catch(err){
            return res.status(500).json({ error: (err as Error).message });
        }
    }
}