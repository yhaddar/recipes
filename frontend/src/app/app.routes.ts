import { Routes } from '@angular/router';
import {HomeComponent} from './features/home/home.component';
import {BlogsComponent} from './features/blogs/blogs.component';
import {RecipesComponent} from './features/recipes/recipes.component';
import {RecipeDetailComponent} from './features/recipe-detail/recipe-detail.component';

export const routes: Routes = [
  {
    path: "",
    pathMatch: 'full',
    component: HomeComponent,
  },
  {
    path: "recipes",
    component: RecipesComponent,
  },
  {
    path: "blogs",
    component: BlogsComponent
  },
  {
    path: "recipe/:id",
    component: RecipeDetailComponent,
  }
];
