export class CategoryDTO {
    private _id!: string;
    private _category_title!: string;
    private _category_image!: string;

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get category_title(): string {
        return this._category_title;
    }

    set category_title(value: string) {
        if(!value) throw new Error("Category Title is required");
        else if(!/^[a-zA-Z\s]+$/.test(value)) throw new Error("Category Title invalid");
        this._category_title = value;
    }

    get category_image(): string {
        return this._category_image;
    }

    set category_image(value: string) {
        this._category_image = value;
    }
}