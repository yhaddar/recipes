import {Express} from "express";
import {sequelize} from "./config/db";
import RecipesRoutes from "./routes/recipeRouter";
import UserRoutes from "./routes/userRouter";
import CategoryRoutes from "./routes/categoryRouter";
import IngredientRoutes from "./routes/ingredientRouter";
import IngredientItemRoutes from "./routes/ingredientItemRouter";
import DirectionRoutes from "./routes/directionRouter";

import express = require("express");
import dotenv = require("dotenv");
import cors = require("cors");

const app: Express = express();
dotenv.config();
app.use(cors());

app.use(express.json());
app.use(express.static("./public"));

app.use("/recipes", RecipesRoutes);
app.use(UserRoutes);
app.use("/category", CategoryRoutes);
app.use("/ingredient", IngredientRoutes);
app.use("/ingredient-item", IngredientItemRoutes);
app.use("/direction", DirectionRoutes);

sequelize.authenticate().then(r => r);

sequelize.sync({force: false}).then(r => r);

app.listen(process.env.APP_PORT);