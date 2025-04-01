export class DirectionDTO {
    private _title!: string;
    private _description!: string;
    private _image!: string;
    private _recipe_id!: string;


    get title(): string {
        return this._title;
    }

    set title(value: string) {
        if(!value) throw new Error("title is required");
        else if(!/^[a-zA-Z\s]+$/.test(value)) throw new Error("title is invalid");
        this._title = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        if(!value) throw new Error("description is required");
        else if(!/^[a-zA-Z0-9\s.,!?'-]{10,}$/.test(value)) throw new Error("description is invalid");
        this._description = value;
    }

    get image(): string {
        return this._image;
    }

    set image(value: string) {
        this._image = value;
    }

    get recipe_id(): string {
        return this._recipe_id;
    }

    set recipe_id(value: string) {
        if(!value) throw new Error("recipe is required");
        else if(!/^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/.test(value)) throw new Error("recipe is invalid");
        this._recipe_id = value;
    }
}