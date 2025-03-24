import {Component, Input, OnInit, signal} from '@angular/core';
import {SubstringPipe} from '../../../shared/pipes/substring.pipe';
import {NgOptimizedImage} from '@angular/common';
import {RouterLink} from '@angular/router';
import { Recipe } from '../../../core/models/recipes.model';

@Component({
  selector: 'app-recipe',
  imports: [
    SubstringPipe,
    NgOptimizedImage,
    RouterLink
  ],
  templateUrl: './recipe.component.html',
  styleUrl: './recipe.component.css'
})
export class RecipeComponent {
  @Input() recipe = signal<any>({});
  @Input() path_image!: string;
}
