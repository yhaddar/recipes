import { Component } from '@angular/core';
import {TitleDescriptionComponent} from '../../../layout/title-description/title-description.component';
import {NgOptimizedImage} from '@angular/common';
import {ButtonBlackComponent} from '../../../layout/Buttons/button-black/button-black.component';

@Component({
  selector: 'app-instagram',
  imports: [
    TitleDescriptionComponent,
    NgOptimizedImage,
    ButtonBlackComponent
  ],
  templateUrl: './instagram.component.html',
  styleUrl: './instagram.component.css'
})
export class InstagramComponent {
  protected posts: string[] = ["post-1", "post-2", "post-3", "post-4"];
}
