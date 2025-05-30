import {Component, Input, signal} from '@angular/core';
import {RecipeComponent} from '../../../layout/cards/recipe/recipe.component';
import {RecipeResponse} from '../../../core/models/recipes.model';
import {TitleDescriptionComponent} from '../../../layout/title-description/title-description.component';

@Component({
  selector: 'app-recipes',
  imports: [
    RecipeComponent,
    TitleDescriptionComponent
  ],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css'
})
export class RecipesComponent {
  @Input() recipes!: RecipeResponse | null;
  @Input() path_image!: string;

  protected readonly signal = signal;
  protected readonly title: string = "simple and tasty recipes";
  protected readonly description: string = "Lorem ipsum dolor sit amet, consectetuipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqut enim ad minim";
}
