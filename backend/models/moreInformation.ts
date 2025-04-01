import {DataTypes, Model, Sequelize} from "sequelize";

class MoreInformation extends Model {
    private _id!: string;
    private _calories!: number;
    private _total_fat!: number;
    private _protein!: number;
    private _carbohydrate!: number;
    private _cholesterol!: number;
    private _recipe_id!: string;
    private _description!: number;


    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get calories(): number {
        return this._calories;
    }

    set calories(value: number) {
        this._calories = value;
    }

    get total_fat(): number {
        return this._total_fat;
    }

    set total_fat(value: number) {
        this._total_fat = value;
    }

    get protein(): number {
        return this._protein;
    }

    set protein(value: number) {
        this._protein = value;
    }

    get carbohydrate(): number {
        return this._carbohydrate;
    }

    set carbohydrate(value: number) {
        this._carbohydrate = value;
    }

    get cholesterol(): number {
        return this._cholesterol;
    }

    set cholesterol(value: number) {
        this._cholesterol = value;
    }

    get recipe_id(): string {
        return this._recipe_id;
    }

    set recipe_id(value: string) {
        this._recipe_id = value;
    }

    get description(): number {
        return this._description;
    }

    set description(value: number) {
        this._description = value;
    }

    static createTables(sequelize: Sequelize) {
        this.init({
            id: {
                type: DataTypes.UUID,
                defaultValue: DataTypes.UUIDV4,
                primaryKey: true,
            },
            calories: {
                type: DataTypes.DOUBLE,
                defaultValue: 0,
            },
            total_fat: {
                type: DataTypes.DOUBLE,
                defaultValue: 0,
            },
            protein: {
                type: DataTypes.DOUBLE,
                defaultValue: 0,
            },
            carbohydrate: {
                type: DataTypes.DOUBLE,
                defaultValue: 0,
            },
            cholesterol: {
                type: DataTypes.DOUBLE,
                defaultValue: 0,
            },
            description: {
                type: DataTypes.STRING,
            },
        },
            {
            sequelize,
            tableName: "moreInformation",
            timestamps: true,
        })
    }

    static recipeRelation(models: any) {
        this.belongsTo(models.Recipes, {
            foreignKey: 'recipe_id',
        })
    }
}

export default MoreInformation;