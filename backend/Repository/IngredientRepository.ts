import {sequelize} from "../config/db";

export class IngredientRepository {
    public async findAllIngredient(recipe_id: any){
        return await sequelize.query(`
            SELECT i.title, i.description,
                   JSON_ARRAYAGG(JSON_OBJECT('title', it.title)) AS IngredientItem
            FROM Ingredients i
                     INNER JOIN ingredient_item it ON i.id = it.ingredient_id
            WHERE recipe_id = ?
            GROUP BY i.id;
        `, {
            replacements: [recipe_id]
        });
    }
}