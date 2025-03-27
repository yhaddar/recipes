import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-header',
    imports: [
        RouterLink
    ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  pages: string[] = ["home", "recipes", "blogs", "contact", "about us"];
  icons: string[] = ["fa-facebook-f", "fa-twitter", "fa-instagram"];
  public logo: string = "foodileand.";
}
