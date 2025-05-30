import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-image-nutrition-information',
  imports: [],
  templateUrl: './image-nutrition-information.component.html',
  styleUrl: './image-nutrition-information.component.css'
})
export class ImageNutritionInformationComponent {
  @Input() path_image!: string;
  @Input() image!: string | undefined;
  @Input() protein: number | undefined = 0;
  @Input() calories: number | undefined = 0;
  @Input() total_fat: number | undefined = 0;
  @Input() cholesterol: number | undefined = 0;
  @Input() description: string | undefined = '';
  @Input() carbohydrate: number | undefined = 0;
}
