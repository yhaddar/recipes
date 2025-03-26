import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-button-black',
  imports: [],
  templateUrl: './button-black.component.html',
  styleUrl: './button-black.component.css'
})
export class ButtonBlackComponent {
  @Input() title!: string;
  @Input() icon!: string;
}
