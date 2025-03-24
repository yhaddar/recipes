import {Component, Input, signal} from '@angular/core';
import {RecipeComponent} from '../../../layout/cards/recipe/recipe.component';
import { Recipe } from '../../../core/models/recipes.model';

@Component({
  selector: 'app-recipes',
  imports: [
    RecipeComponent
  ],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css'
})
export class RecipesComponent {
  @Input() recipes = signal<(Recipe | null)>(null);
  @Input() path_image!: string;
}
