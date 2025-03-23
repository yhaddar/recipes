import { Request, Response } from "express";
import {UserDTO} from "../DTO/userDTO";
import bcrypt from "bcryptjs";
import User from "../models/user";

export class UserController {
    public async register(req: Request, res: Response, file_name: string): Promise<any> {
        try {

            const userDTO = new UserDTO();
            userDTO.full_name = req.body.full_name;
            userDTO.email = req.body.email;
            userDTO.password = req.body.password;
            userDTO.profile = file_name;

            const salt: string = await bcrypt.genSalt();
            const hashPassword: string= await bcrypt.hash(userDTO.password, salt);

            const save: User = await User.create({
                full_name: userDTO.full_name,
                email: userDTO.email,
                password: hashPassword,
                profile: userDTO.profile,
            });

            if(save)
                return res.status(200).json({ data: "your account has been created successfully" });
            else
                throw new Error("failed to create your account");


        }catch(err){
            return res.status(500).json({ error: (err as Error).message });
        }
    }
}