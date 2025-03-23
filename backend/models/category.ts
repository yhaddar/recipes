import {DataTypes, Model, Sequelize} from "sequelize";

class Category extends Model {
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
        this._category_title = value;
    }

    get category_image(): string {
        return this._category_image;
    }

    set category_image(value: string) {
        this._category_image = value;
    }

    static createTables(sequelize: Sequelize) {
        this.init({
                id: {
                    type: DataTypes.UUID,
                    defaultValue: DataTypes.UUIDV4,
                    primaryKey: true,
                },
                category_title: {
                    type: DataTypes.STRING,
                    allowNull: false,
                    unique: {
                        name: "category_title_unique",
                        msg: "Category Title already exists"
                    }
                },
                category_image: {
                    type: DataTypes.STRING,
                }
            },
            {
                sequelize,
                tableName: "categories",
                timestamps: true
            });
    }

    static recipesRelation(models: any){
        this.hasMany(models.Recipes, { foreignKey: 'category_id', as: 'recipes' });
    }
}

export default Category;