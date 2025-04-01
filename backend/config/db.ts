import { Sequelize } from 'sequelize';
import dotenv = require('dotenv');
import User from "../models/user";
import Categories from "../models/category";
import Recipes from "../models/recipe";
import MoreInformation from "../models/moreInformation";
import Ingredient from "../models/Ingredient";
import IngredientItem from "../models/ingredientItem";
import Direction from "../models/direction";

dotenv.config();

export const sequelize = new Sequelize(
    process.env.DB_DATABASE as string,
    process.env.DB_USER as string,
    process.env.DB_PASSWORD as string,
    {
        dialect: 'mysql',
        host: process.env.DB_HOST as string,
    }
);

// create tables :
User.createTables(sequelize);
Categories.createTables(sequelize);
Recipes.createTables(sequelize);
MoreInformation.createTables(sequelize);
Ingredient.createTables(sequelize);
IngredientItem.createTables(sequelize);
Direction.createTables(sequelize);

// les relation :
User.recipesRelation({ Recipes });
Recipes.userRelation({ User });
Recipes.categoryRelation({ Categories });
Categories.recipesRelation({ Recipes });
MoreInformation.recipeRelation({ Recipes });
Ingredient.recipeRelation({ Recipes });
IngredientItem.ingredientRelation({ Ingredient });
Direction.recipeRelation({ Recipes });
