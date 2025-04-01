import {sequelize} from "../config/db";

export class DirectionRepository {

    public async getDirectionByRecipe(recipe_id: any) {
        return await sequelize.query(`SELECT * FROM direction WHERE recipe_id = ?`, {
            replacements: [recipe_id]
        })
    }
}