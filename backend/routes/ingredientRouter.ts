import {Router, Request, Response} from "express";
import {IngredientController} from "../controller/ingredientController";

const router: Router = Router();

const ingredientController = new IngredientController();

router.post("/add", async (req: Request, res: Response) => {
   return await ingredientController.setIngredient(req, res);
});

router.get("/", async (req: Request, res: Response) => {
   return await ingredientController.getIngredient(req, res);
})

export default router;