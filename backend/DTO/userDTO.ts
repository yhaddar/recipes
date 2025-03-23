export class UserDTO {
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
        if(!value) throw new Error("full name is required");
        else if(!/^[a-zA-Z\s]+$/.test(value)) throw new Error("full name is invalid");
        this._full_name = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        if(!value) throw new Error("email is required");
        else if(!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value)) throw new Error("email is invalid");
        this._email = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        if(!value) throw new Error("password is required");
        else if(!/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(value)) throw new Error("password is invalid");
        this._password = value;
    }

    get profile(): string {
        return this._profile;
    }

    set profile(value: string) {
        this._profile = !value ? "user.png" : value;
    }
}