import {sequelize} from "../config/db";
import Recipe from "../models/recipe";
import {Op} from "sequelize";

export class RecipeRepository {

    public async filterRecipeWithCategory(category: any, limit: number, offset: number) {
        return await sequelize.query(
            `SELECT r.*,
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
        return await sequelize.query(`
            SELECT r.*,
                   JSON_OBJECT('category_title', c.category_title, 'category_image', c.category_image) AS Category,
                   JSON_OBJECT('full_name', u.full_name, 'email', u.email, 'profile', u.profile) AS User
            FROM recipes r
                INNER JOIN categories c
            ON r.category_id = c.id
                INNER JOIN users u ON u.id = r.user_id
            WHERE title LIKE :search`,
            {
                replacements: { search: `%${search}%` }
            }
        );
    }

    public async recipeById(id: any){
        return await sequelize.query(
            `SELECT r.*,
                    JSON_OBJECT('category_title', c.category_title, 'category_image', c.category_image, 'id', c.id) AS Category,
                    JSON_OBJECT('full_name', u.full_name, 'email', u.email, 'profile', u.profile) AS User,
                    JSON_OBJECT('calories', m.calories, 'total_fat', m.total_fat, 'protein', m.protein, 'carbohydrate', m.carbohydrate, 'cholesterol', m.cholesterol, 'description', m.description) AS Nutrition
             FROM recipes r
                 INNER JOIN categories c ON r.category_id = c.id
                 INNER JOIN users u ON u.id = r.user_id
                 LEFT JOIN moreInformation m ON r.id = m.recipe_id
             WHERE r.id = ?`,
            {
                replacements: [id]
            }
        );
    }

    public async recipeByCategory(category: any, limit: number, offset: number) {
        return await sequelize.query(`
            SELECT r.id,
                   r.title,
                   r.image,
                   r.description,
                   r.prep_time,
                   JSON_OBJECT('category_title', c.category_title, 'category_image', c.category_image) AS Category,
                   JSON_OBJECT('full_name', u.full_name) AS User

            FROM recipes r
                     LEFT JOIN categories c ON c.id = r.category_id
                    INNER JOIN users u ON u.id = r.user_id
            WHERE r.category_id = ?
            ORDER BY r.createdAt DESC LIMIT ? OFFSET ?;`, {
            replacements: [category, limit, offset]
        });
    }

}