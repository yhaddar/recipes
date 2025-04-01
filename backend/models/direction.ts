import {DataTypes, Model, Sequelize} from "sequelize";

class Direction extends Model {
    private _id!: string;
    private _title!: string;
    private _description!: string;
    private _image!: string;
    private _recipe_id!: string;


    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        this._title = value;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
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
        this._recipe_id = value;
    }

    static createTables(sequelize: Sequelize) {
        this.init({
            id: {
                type: DataTypes.UUID,
                defaultValue: DataTypes.UUIDV4,
                primaryKey: true,
            },
            title: {
                type: DataTypes.STRING,
            },
            description: {
                type: DataTypes.STRING(750),
            },
            image: {
                type: DataTypes.STRING,
                defaultValue: null,
            },
            recipe_id: {
                type: DataTypes.UUID,
            }
        }, {
            sequelize,
            tableName: 'direction',
            timestamps: true
        });
    }

    static recipeRelation(models: any){
        this.belongsTo(models.Recipes, { foreignKey: 'recipe_id' });
    }
}

export default Direction;