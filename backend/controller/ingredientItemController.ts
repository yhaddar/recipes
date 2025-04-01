import { Request, Response } from "express";
import {IngredientItemDTO} from "../DTO/ingredientItemDTO";
import IngredientItem from "../models/ingredientItem";

export class IngredientItemController {
    public async setIngredientItem(req: Request, res: Response): Promise<any> {
        try{

            const ingredientItemDTO: IngredientItemDTO = new IngredientItemDTO();
            ingredientItemDTO.title = req.body.title;
            ingredientItemDTO.ingredient_id = req.body.ingredient_id;

            const ingredient: IngredientItem = await IngredientItem.create({
                title: ingredientItemDTO.title,
                ingredient_id: req.body.ingredient_id,
            });

            if (ingredient)
                return res.status(201).json({ message: "ingredient created" });
            else
                throw new Error("failed to create ingredient");

        }catch(err){
            return res.status(500).json({ error: (err as Error).message });
        }
    }
}