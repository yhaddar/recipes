import { Request, Response } from 'express';
import {DirectionDTO} from "../DTO/directionDTO";
import Direction from "../models/direction";
import {DirectionRepository} from "../Repository/DirectionRepository";

export class DirectionController {

    private directionRepository: DirectionRepository;

    public constructor() {
        this.directionRepository = new DirectionRepository();
    }

    public async setDirection(req: Request, res: Response, image: string): Promise<any> {
        try {

            const directionDTO: DirectionDTO = new DirectionDTO();
            directionDTO.title = req.body.title;
            directionDTO.description = req.body.description;
            directionDTO.recipe_id = req.body.recipe_id;

            const save: Direction = await Direction.create({
                title: directionDTO.title,
                description: directionDTO.description,
                recipe_id: directionDTO.recipe_id,
                image: image || null
            });

            if(save)
                return res.status(201).json({ message: "Direction created" });
            else
                throw new Error("failed to create new direction");

        }catch(err) {
            res.status(500).json({ error: (err as Error).message });
        }
    }

    public async getDirection(req: Request, res: Response): Promise<any> {
        try {
            const { recipe_id } = req.query;
            const [direction] = await this.directionRepository.getDirectionByRecipe(recipe_id);

            return res.status(200).json({ data: direction });
        }catch(err) {
            res.status(500).json({ error: (err as Error).message });
        }
    }
}