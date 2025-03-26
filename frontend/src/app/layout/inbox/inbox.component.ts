import { Component } from '@angular/core';
import {ButtonBlackComponent} from '../Buttons/button-black/button-black.component';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-inbox',
  imports: [
    ButtonBlackComponent,
    NgOptimizedImage
  ],
  templateUrl: './inbox.component.html',
  styleUrl: './inbox.component.css'
})
export class InboxComponent {

}
