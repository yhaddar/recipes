import {Component, EventEmitter, Input, OnInit, Output, signal} from '@angular/core';
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
export class RecipesComponent implements OnInit {
  @Input() recipes!: RecipeResponse | null;
  @Input() path_image!: string;

  @Output() page: EventEmitter<number> = new EventEmitter<number>();

  protected page_default = signal<number>(0);

  protected readonly signal = signal;
  protected readonly title: string = "simple and tasty recipes";
  protected readonly description: string = "Lorem ipsum dolor sit amet, consectetuipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqut enim ad minim";

  increment(): void {
    this.page_default.set(this.page_default() + 1);
    this.page.emit(this.page_default());

    console.log(this.page_default() + " " + this.recipes?.lastPage);
  }

  decrement(): void {
    this.page_default.set(this.page_default() - 1);
    console.log(this.page_default() + " " + this.recipes?.lastPage);
  }

  ngOnInit(): void {
  }

}
