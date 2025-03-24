import {Category} from './Category.model';

interface User {
  full_name: string;
  profile: string;
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
}

export interface RecipeResponse {
  data: Recipe[] | null,
  page: number,
  lastPage: number,
  totalItems: number,
}
