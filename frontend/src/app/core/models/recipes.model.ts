import {Category} from './Category.model';

interface User {
  full_name: string;
  profile: string;
}

export interface Nutrition {
  protein: number;
  calories: number;
  total_fat: number;
  cholesterol: number;
  carbohydrate: number;
  description: string;
}

export interface Recipe {
  id: string;
  Category: Category,
  User: User ,
  title: string,
  image: string,
  description: string,
  prep_time: number,
  cook_time: number,
  user_id: string,
  category_id: string,
  createdAt: string,
  Nutrition: Nutrition
}

export interface RecipeResponse {
  data: Recipe[] | null,
  page: number,
  lastPage: number,
  totalItems: number,
}

export interface IngredientItem  {
  title: string | null;
}
export interface Ingredient {
  id: string | null;
  title: string | null;
  description: string | null;
  IngredientItem: IngredientItem[]
}

export interface Direction {
  title: string;
  description: string;
  image: string;
}
