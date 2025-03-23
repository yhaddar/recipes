export class RecipesDTO {
    private _title!: string;
    private _image!: string;
    private _description!: string;
    private _prep_time!: number;
    private _cook_time!: number;
    private _user_id!: string;
    private _category_id!: string;

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        if(!value) throw new Error("Title is required");
        else if(!/^[a-zA-Z0-9\s?]+$/.test(value)) throw new Error("Title is invalid");

        this._title = value;
    }

    get image(): string {
        return this._image;
    }

    set image(value: string) {
        if(!value) throw new Error("Image is required");
        this._image = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        if(!value) throw new Error("Description is required");
        else if(!/^[a-zA-Z0-9\s.,!?'-]{10,}$/.test(value)) throw new Error("Description is invalid");

        this._description = value;
    }

    get prep_time(): number {
        return this._prep_time;
    }

    set prep_time(value: number) {
        if (!value) throw new Error("Preparation time is required");
        this._prep_time = value;
    }

    get cook_time(): number {
        return this._cook_time;
    }

    set cook_time(value: number) {
        if(!value) throw new Error("Cook time is required");
        this._cook_time = value;
    }

    get user_id(): string {
        return this._user_id;
    }

    set user_id(value: string) {
        if(!value) throw new Error("User is required");
        this._user_id = value;
    }

    get category_id(): string {
        return this._category_id;
    }

    set category_id(value: string) {
        if(!value) throw new Error("Category is required");
        this._category_id = value;
    }
}