import {Component, Input} from '@angular/core';
import {OtherRecipeComponent} from '../../../layout/cards/other-recipe/other-recipe.component';
import {Direction, Ingredient, Recipe, RecipeResponse} from '../../../core/models/recipes.model';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-ingredient-direction',
  imports: [
    OtherRecipeComponent,
    NgForOf
  ],
  templateUrl: './ingredient-direction.component.html',
  styleUrl: './ingredient-direction.component.css'
})
export class IngredientDirectionComponent {
  @Input() recipes!: RecipeResponse | null;
  @Input() path_image!: string;
  @Input() ingredients!: Ingredient[] | null;
  @Input() directions!: Direction[] | null;

  protected index: number = 1;
}
