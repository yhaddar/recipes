import {Router, Request, Response} from "express";
import {UserController} from "../controller/userController";
import multer = require("multer");
import {Multer, StorageEngine} from "multer";

let file_name: string = "";
const storage: StorageEngine = multer.diskStorage({
    destination: (req, file, callback) => {
        callback(null, "public/users");
    },
    filename: (req, file, callback) => {
        let filename: string = Date.now() + "_" + file.originalname;
        callback(null, filename);
        file_name = filename;
    }
});


const upload: Multer = multer({ storage: storage });

const userController = new UserController();
const router: Router = Router();

router.post("/register", upload.single("profile"), async (req: Request, res: Response): Promise<any> => {
    return await userController.register(req, res, file_name);
})

export default router;