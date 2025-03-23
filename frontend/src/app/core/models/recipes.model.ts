interface Category {
  category_title: string;
  category_image: string;
}

interface User {
  full_name: string;
  profile: string;
}

export interface Recipe {
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
