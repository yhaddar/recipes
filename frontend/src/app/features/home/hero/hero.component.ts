import {Component, Input, OnInit, signal} from '@angular/core';
import {Recipe, RecipeResponse} from '../../../core/models/recipes.model';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {DatePipe} from '../../../shared/pipes/date.pipe';
import {SubstringPipe} from '../../../shared/pipes/substring.pipe';
import {ButtonBlackComponent} from '../../../layout/Buttons/button-black/button-black.component';

@Component({
  selector: 'app-hero',
  imports: [
    NgOptimizedImage,
    DatePipe,
    NgIf,
    SubstringPipe,
    ButtonBlackComponent,
  ],
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.css'
})
export class HeroComponent {
  @Input() recipe!: RecipeResponse | null;
  @Input() path_image!: string;
  protected index: number = 0;
}
