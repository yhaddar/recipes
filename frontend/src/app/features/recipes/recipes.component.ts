import {Component, inject, OnInit, signal} from '@angular/core';
import {HeroComponentComponent} from "../../layout/hero-component/hero-component.component";
import {RecipesService} from '../../core/services/recipes.service';
import {RecipeResponse} from '../../core/models/recipes.model';
import {APP} from '../../../envirenement/server';
import {Recipes2Component} from "../home/recipes2/recipes2.component";
import {Button} from "primeng/button";
import {CategoriesService} from "../../core/services/categories.service";
import {Category} from "../../core/models/Category.model";
import {Carousel} from "primeng/carousel";

@Component({
  selector: 'app-recipes',
  imports: [
    HeroComponentComponent,
    Recipes2Component,
    Carousel,
  ],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css'
})
export class RecipesComponent implements OnInit {
  private recipesService: RecipesService = inject(RecipesService);
  private categoriesService: CategoriesService = inject(CategoriesService);

  protected recipes = signal<RecipeResponse | null>(null)
  protected categories = signal<Category[] | []>([])
  protected path_image: string = `${APP.SERVER_HOST}:${APP.SERVER_PORT}`;


  getAllRecipes(): void {
    this.recipesService.getAllRecipes(0, 16).subscribe((recipe: RecipeResponse) => {
      this.recipes.set(recipe);
    });
  }

  getAllCategories(): void {
    this.categoriesService.getAllCategories().subscribe((category: { data: Category[] }) => {
      this.categories.set(category?.data)
    });
  }

  ngOnInit(): void {
    this.getAllRecipes();
    this.getAllCategories();
  }
}
