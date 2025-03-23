import { Response, Request } from "express";
import {CategoryDTO} from "../DTO/categoryDTO";
import Category from "../models/category";

export class CategoryController {
    public async setCategory(res: Response, req: Request, file_name: string): Promise<any> {
        try{

            const categoryDTO = new CategoryDTO();
            categoryDTO.category_title = req.body.category_title;
            categoryDTO.category_image = file_name;

            const save: Category = await Category.create({
                category_title: categoryDTO.category_title,
                category_image: categoryDTO.category_image,
            });

            if(save)
                return res.status(200).json({ data: "the category was created successfully" });
            else
                throw new Error("failed to create a category");

        }catch(err){
            return res.status(500).json({ error: (err as Error).message });
        }
    }
}