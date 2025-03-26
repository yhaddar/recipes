import {Component, Input, signal} from '@angular/core';
import {RecipeComponent} from '../recipe/recipe.component';
import {NgOptimizedImage} from '@angular/common';
import {RouterLink} from '@angular/router';
import {SubstringPipe} from '../../../shared/pipes/substring.pipe';
import {Recipe} from '../../../core/models/recipes.model';

@Component({
  selector: 'app-recipe-without-background',
  imports: [
    NgOptimizedImage,
    RouterLink,
    SubstringPipe
  ],
  templateUrl: './recipe-without-background.component.html',
  styleUrl: './recipe-without-background.component.css'
})
export class RecipeWithoutBackgroundComponent {
  @Input() recipe = signal<any>({});
  @Input() path_image!: string;
}
