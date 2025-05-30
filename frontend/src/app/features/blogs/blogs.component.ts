import { Component } from '@angular/core';
import {HeroComponentComponent} from "../../layout/hero-component/hero-component.component";

@Component({
  selector: 'app-blogs',
    imports: [
        HeroComponentComponent
    ],
  templateUrl: './blogs.component.html',
  styleUrl: './blogs.component.css'
})
export class BlogsComponent {

}
