import {Component, Input} from '@angular/core';
import {SubstringPipe} from '../../../shared/pipes/substring.pipe';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-other-recipe',
  imports: [
    SubstringPipe,
    RouterLink
  ],
  templateUrl: './other-recipe.component.html',
  styleUrl: './other-recipe.component.css'
})
export class OtherRecipeComponent {
  @Input() title!: string;
  @Input() full_name!: string;
  @Input() image!: string;
  @Input() path_image!: string;
  @Input() id!: string;
}
