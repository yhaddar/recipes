import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {ButtonBlackComponent} from '../Buttons/button-black/button-black.component';

@Component({
  selector: 'app-learn-more',
  imports: [
    NgOptimizedImage,
    ButtonBlackComponent
  ],
  templateUrl: './learn-more.component.html',
  styleUrl: './learn-more.component.css'
})
export class LearnMoreComponent {
  protected title: string = "Everyone can be a chef in their own kitchen";
  protected description: string = "Lorem ipsum dolor sit amet, consectetuipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqut enim ad minim "
}
