import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {HeaderModule} from '../../core/models/header.module';

@Component({
  selector: 'app-header',
    imports: [
        RouterLink
    ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  pages: HeaderModule[] = [
    {
      id: 1,
      title: "home",
      path: "/"
    },
    {
      id: 2,
      title: "recipes",
      path: "/recipes",
    },
    {
      id: 3,
      title: "blog",
      path: "/blog",
    },
    {
      id: 4,
      title: "contact",
      path: "/contact",
    },
    {
      id: 5,
      title: "about us",
      path: "/about"
    }
  ];
  icons: string[] = ["fa-facebook-f", "fa-twitter", "fa-instagram"];
  public logo: string = "foodileand.";
}
