import Recipe from "../models/recipe";

export class RecipeResponse {
    private _page!: number;
    private _totalItems!: number;
    private _recipes!: Recipe[];
    private _lastPage!: number;


    get page(): number {
        return this._page;
    }

    set page(value: number) {
        this._page = value;
    }

    get totalItems(): number {
        return this._totalItems;
    }

    set totalItems(value: number) {
        this._totalItems = value;
    }

    get recipes(): Recipe[] {
        return this._recipes;
    }

    set recipes(value: Recipe[]) {
        this._recipes = value;
    }

    get lastPage(): number {
        return this._lastPage;
    }

    set lastPage(value: number) {
        this._lastPage = value;
    }

    public toString(): Object {
        return {
            page: this.page,
            totalItems: this.totalItems,
            data: this._recipes ?? "no recipes found",
            lastPage: this.lastPage,
        }
    }

}