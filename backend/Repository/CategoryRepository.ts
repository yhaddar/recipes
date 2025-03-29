import {sequelize} from "../config/db";

export class CategoryRepository {

    public async getAllCategoryWithCounts(){
        return await sequelize.query('SELECT c.*, COUNT(r.id) AS recipe_count FROM categories c LEFT JOIN recipes r ON c.id = r.category_id GROUP BY c.id');
    }

}