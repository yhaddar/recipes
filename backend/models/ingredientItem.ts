import {DataTypes, Model, Sequelize} from "sequelize";

class IngredientItem extends Model {
    private _id!: string;
    private _title!: string;
    private _ingredient_id!: string;

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


    get ingredient_id(): string {
        return this._ingredient_id;
    }

    set ingredient_id(value: string) {
        this._ingredient_id = value;
    }

    static createTables(sequelize: Sequelize) {
        this.init({
            id: {
                type: DataTypes.UUID,
                defaultValue: DataTypes.UUIDV4,
                primaryKey: true,
                unique: true,
            },
            title: {
                type: DataTypes.STRING,
            },
            ingredient_id: {
                type: DataTypes.UUID,
            }
        }, {
            sequelize,
            tableName: 'ingredient_item',
            timestamps: true,
        });
    }

    static ingredientRelation(models: any) {
        this.belongsTo(models.Ingredient, {
            foreignKey: 'ingredient_id'
        })
    }
}
export default IngredientItem;