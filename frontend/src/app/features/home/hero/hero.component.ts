import {Component, Input} from '@angular/core';
import {Recipe} from '../../../core/models/recipes.model';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {DatePipe} from '../../../shared/pipes/date.pipe';
import {SubstringPipe} from '../../../shared/pipes/substring.pipe';

@Component({
  selector: 'app-hero',
  imports: [
    NgOptimizedImage,
    DatePipe,
    NgIf,
    SubstringPipe,
  ],
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.css'
})
export class HeroComponent {
  @Input() recipe!: (Recipe | null)[];
  @Input() path_image!: string;
  public index: number = 0;
}
