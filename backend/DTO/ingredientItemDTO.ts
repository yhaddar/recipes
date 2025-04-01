export class IngredientItemDTO {
    private _title!: string;
    private _ingredient_id!: string;

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        if(!value) throw new Error("title is required");
        else if(!/^[a-zA-Z\s]+$/.test(value)) throw new Error("title is invalid");

        this._title = value;
    }


    get ingredient_id(): string {
        return this._ingredient_id;
    }

    set ingredient_id(value: string) {
        if(!value) throw new Error("ingredient is required");
        else if(!/^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/.test(value)) throw new Error("ingredient is invalid");
        this._ingredient_id = value;
    }

}