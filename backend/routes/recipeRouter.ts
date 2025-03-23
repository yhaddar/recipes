import { Router, Request, Response } from "express";
import { RecipeController } from "../controller/recipeController";
import multer = require("multer");
import {Multer} from "multer";

const router: Router = Router();
const recipeController = new RecipeController();

let file_name: string = "";
const storage = multer.diskStorage({
   destination: (req, file, callback) => callback(null, "public/recipes"),
   filename: (req, file, callback) => {
       const filename = Date.now() + "_" + file.originalname;
       callback(null, filename);
       file_name = filename
   }
});

const upload: Multer = multer({ storage });

router.get("/", async (req: Request, res: Response): Promise<any> => {
    return await recipeController.getAllRecipes(res);
});

router.post("/add", upload.single("image"), async (req: Request, res: Response) => {
    return await recipeController.setRecipe(req, res, file_name);
})

export default router;