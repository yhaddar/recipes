import {Component, Input, signal} from '@angular/core';
import {TitleDescriptionComponent} from '../../../layout/title-description/title-description.component';
import {
  RecipeWithoutBackgroundComponent
} from '../../../layout/cards/recipe-without-background/recipe-without-background.component';
import {Recipe, RecipeResponse} from '../../../core/models/recipes.model';

@Component({
  selector: 'app-recipes2',
  imports: [
    TitleDescriptionComponent,
    RecipeWithoutBackgroundComponent
  ],
  templateUrl: './recipes2.component.html',
  styleUrl: './recipes2.component.css'
})
export class Recipes2Component {
  @Input() recipes!: RecipeResponse | null;
  @Input() path_image!: string;
  @Input() showTitle: boolean = true;
  @Input() withPaginate: boolean = false;

  @Input() title!: string;

  protected readonly signal = signal;
}
