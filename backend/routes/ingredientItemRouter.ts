import {Router, Request, Response} from "express";
import {IngredientItemController} from "../controller/ingredientItemController";

const router: Router = Router();

const ingredientItemController = new IngredientItemController();

router.post("/add", async (req: Request, res: Response) => {
    return await ingredientItemController.setIngredientItem(req, res);
});

export default router;