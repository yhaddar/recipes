import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-title-description',
  imports: [],
  templateUrl: './title-description.component.html',
  styleUrl: './title-description.component.css'
})
export class TitleDescriptionComponent {
  @Input() title!: string;
  @Input() description!: string;
}
