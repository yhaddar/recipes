import {Router, Request, Response} from "express";
import {CategoryController} from "../controller/categoryController";
import {Multer, StorageEngine} from "multer";

import multer = require("multer");

const router: Router = Router();
const categoryController = new CategoryController();

let file_name: string = "";
const storage: StorageEngine = multer.diskStorage({
    destination: (req, file, callback) => {
        callback(null, "public/categories");
    },
    filename: (req, file, callback) => {
        let filename = Date.now() + "_" + file.originalname;
        callback(null, filename);
        file_name = filename;
    }
});

const upload: Multer = multer({ storage });


router.post("/add", upload.single("category_image"), async (req: Request, res: Response): Promise<any> => {
    return await categoryController.setCategory(res, req, file_name);
});

router.get("/", async (req: Request, res: Response): Promise<any> => {
    return await categoryController.getAllCategories(res);
})

export default router;