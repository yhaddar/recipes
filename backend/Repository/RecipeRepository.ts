import {sequelize} from "../config/db";
import Recipe from "../models/recipe";
import {Op} from "sequelize";

export class RecipeRepository {

    public async filterRecipeWithCategory(category: any, limit: number, offset: number) {
        return await sequelize.query(
            `SELECT *,
                JSON_OBJECT('category_title', category_title, 'category_image', category_image) AS Category FROM recipes r
                INNER JOIN categories c ON r.category_id = c.id WHERE category_id = ?
                ORDER BY r.createdAt DESC LIMIT ? OFFSET ?`, {
            replacements: [category, limit, offset]
        });
    }

    public async getCategoryCountRecipe(id: any){
        return await sequelize.query('SELECT COUNT(r.id) AS recipe_count FROM categories c LEFT JOIN recipes r ON c.id = r.category_id  WHERE c.id = ?', {
            replacements: [id]
        });
    }

    public async searchForRecipe(search: any) {
        return await Recipe.findAll();
    }

}