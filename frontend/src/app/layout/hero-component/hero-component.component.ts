import {Component, Input} from '@angular/core';
import {ButtonBlackComponent} from '../Buttons/button-black/button-black.component';

@Component({
  selector: 'app-hero-component',
  imports: [
    ButtonBlackComponent
  ],
  templateUrl: './hero-component.component.html',
  styleUrl: './hero-component.component.css'
})
export class HeroComponentComponent {
  @Input() title!: string;
  @Input() description!: string;
}
