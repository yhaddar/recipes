import {Express} from "express";
import {sequelize} from "./config/db";
import RecipesRoutes from "./routes/recipeRouter";
import UserRoutes from "./routes/userRouter";
import CategoryRoutes from "./routes/categoryRouter";

import express = require("express");
import dotenv = require("dotenv");
import cors = require("cors");

const app: Express = express();
dotenv.config();

app.use(express.json());
app.use(express.static("./public"));

app.use("/recipes", RecipesRoutes);
app.use(UserRoutes);
app.use("/category", CategoryRoutes);
app.use(cors())

sequelize.authenticate().then(r => console.log(r));

sequelize.sync({force: false}).then(r => console.log(r));

app.listen(process.env.APP_PORT);