import {Router, Request, Response} from "express";
import {DirectionController} from "../controller/directionController";
import multer = require("multer");
import {Multer} from "multer";

const router: Router = Router();

const directionController = new DirectionController();

let filename: string = "";
const storage = multer.diskStorage({
    destination: (req, file, callback) => callback(null, "public/direction"),
    filename: (req, file, callback) => {
        const file_name = Date.now() + "_" + file.originalname;
        callback(null, file_name);
        filename = file_name;
    }
});

const upload: Multer = multer({ storage })

router.post("/add", upload.single("image"), async (req: Request, res: Response) => {
    return await directionController.setDirection(req, res, filename);
});

router.get("/", async (req: Request, res: Response) => {
    return await directionController.getDirection(req, res);
})

export default router;