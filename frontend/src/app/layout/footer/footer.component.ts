import { Component } from '@angular/core';
import {HeaderModel} from '../../core/models/header.model';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-footer',
  imports: [
    RouterLink
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  protected logo: string = "foodileand.";
  protected pages: string[] = ["recipes", "blog", "contact", "about us"];
  protected icons: string[] = ["fa-facebook", "fa-twitter", "fa-instagram"];
  protected copyright: string = "© 2020 Flowbase. Powered by Webflow";
}
