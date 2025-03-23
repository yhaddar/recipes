import {DataTypes, Model, Sequelize} from "sequelize";

class User extends Model {
    private _id!: string;
    private _full_name!: string;
    private _email!: string;
    private _password!: string;
    private _profile!: string;

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get full_name(): string {
        return this._full_name;
    }

    set full_name(value: string) {
        this._full_name = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    get profile(): string {
        return this._profile;
    }

    set profile(value: string) {
        this._profile = value;
    }

    static createTables(sequelize: Sequelize) {
        this.init({
                id: {
                    type: DataTypes.UUID,
                    defaultValue: DataTypes.UUIDV4,
                    primaryKey: true,
                },
                full_name: {
                    type: DataTypes.STRING,
                    allowNull: false,
                },
                email: {
                    type: DataTypes.STRING,
                    unique: {
                        name: "email_unique",
                        msg: 'Email already exists'
                    }
                },
                password: {
                    type: DataTypes.STRING,
                    allowNull: false,
                },
                profile: {
                    type: DataTypes.STRING,
                    allowNull: false,
                    defaultValue: "user.png"
                }
            },
            {
                sequelize,
                tableName: "users",
                timestamps: true
            }
        );
    }
    static recipesRelation(models: any) {
        this.hasMany(models.Recipes, { foreignKey: 'user_id', as: 'recipes' });
    }
}
export default User;
