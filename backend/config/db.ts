import { Sequelize } from 'sequelize';
import dotenv = require('dotenv');
import User from "../models/user";
import Categories from "../models/category";
import Recipes from "../models/recipe";

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

// les relation :
User.recipesRelation({ Recipes });
Recipes.userRelation({ User });
Recipes.categoryRelation({ Categories });
Categories.recipesRelation({ Recipes })