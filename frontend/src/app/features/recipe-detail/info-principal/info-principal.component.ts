import {Component, Input} from '@angular/core';
import {DatePipe} from "../../../shared/pipes/date.pipe";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {Recipe} from '../../../core/models/recipes.model';

@Component({
  selector: 'app-info-principal',
  imports: [
    DatePipe,
    NgOptimizedImage
  ],
  templateUrl: './info-principal.component.html',
  styleUrl: './info-principal.component.css'
})
export class InfoPrincipalComponent {
  @Input() title!: string | undefined;
  @Input() full_name!: string | undefined;
  @Input() profile!: string | undefined;
  @Input() createdAt!: string | undefined;
  @Input() prep_time!: number | undefined;
  @Input() cook_time!: number | undefined;
  @Input() category_image!: string | undefined;
  @Input() category_title!: string | undefined;
  @Input() path_image!: string;

  protected icons: string[] = ["share-alt", "bookmark"]

}
