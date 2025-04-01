import {Component, inject, Input, OnInit, signal} from '@angular/core';
import {DatePipe} from '../../shared/pipes/date.pipe';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {RecipesService} from '../../core/services/recipes.service';
import {Direction, Ingredient, Recipe, RecipeResponse} from '../../core/models/recipes.model';
import { ActivatedRoute } from '@angular/router';
import {APP} from '../../../envirenement/server';
import {InfoPrincipalComponent} from './info-principal/info-principal.component';
import {ImageNutritionInformationComponent} from './image-nutrition-information/image-nutrition-information.component';
import {InboxComponent} from '../../layout/inbox/inbox.component';
import {Recipes2Component} from '../home/recipes2/recipes2.component';
import {IngredientDirectionComponent} from './ingredient-direction/ingredient-direction.component';

@Component({
  selector: 'app-recipe-detail',
  imports: [
    InfoPrincipalComponent,
    ImageNutritionInformationComponent,
    InboxComponent,
    Recipes2Component,
    IngredientDirectionComponent
  ],
  templateUrl: './recipe-detail.component.html',
  styleUrl: './recipe-detail.component.css'
})
export class RecipeDetailComponent implements OnInit {

  protected path_image: string = `${APP.SERVER_HOST}:${APP.SERVER_PORT}`;

  private recipeService: RecipesService = inject(RecipesService);

  protected recipe = signal<Recipe | null>(null);
  private route: ActivatedRoute = inject(ActivatedRoute);
  private id  = this.route.snapshot.paramMap.get("id") as string;
  protected recipeWithCategory1 = signal<RecipeResponse | null>(null);
  protected recipeWithCategory2 = signal<RecipeResponse | null>(null);
  protected ingredients = signal<{data: Ingredient[] } | null>(null);
  protected direction = signal<{data: Direction[] } | null>(null);

  getRecipe() {
    this.recipeService.recipeDetail(this.id).subscribe((recipe) => {
      this.recipe.set(recipe);
    });
  }

  getRecipeWithCategory(page: number, size: number, type: string){
    this.recipeService.recipeWithCategory(this.recipe()!.Category!.id, page, size).subscribe((recipe: RecipeResponse) => {
      type === "other-recipe" ? this.recipeWithCategory1.set(recipe) : this.recipeWithCategory2.set(recipe);
    })
  }

  getIngredient() {
    this.recipeService.getIngredient(this.recipe()?.id).subscribe((ingredient: { data: Ingredient[] }) => {
      this.ingredients.set(ingredient);

    })
  }

  getDirection() {
    this.recipeService.getDirection(this.recipe()?.id).subscribe((direction: { data: Direction[] }) => {
      this.direction.set(direction);
    })
  }

  ngOnInit(): void {
    this.getRecipe();
    this.getRecipeWithCategory(0, 3, 'other-recipe');
    this.getRecipeWithCategory(1, 4, 'recipe-same-category');
    this.getIngredient();
    this.getDirection();

  }

}
