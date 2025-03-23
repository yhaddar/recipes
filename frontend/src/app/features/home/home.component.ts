import {Component, inject, OnInit, signal} from '@angular/core';
import {HeroComponent} from './hero/hero.component';
import {RecipesService} from '../../core/services/recipes.service';
import {Recipe} from '../../core/models/recipes.model';
import {APP} from '../../../envirenement/server';

@Component({
  selector: 'app-home',
  imports: [
    HeroComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private recipesService: RecipesService = inject(RecipesService);
  public recipes = signal<Recipe[] | []>([]);
  public path_image: string = `${APP.SERVER_HOST}:${APP.SERVER_PORT}`;

  ngOnInit(): void {
    this.recipesService.getAllRecipes().subscribe({
      next: (res: { data: Recipe[] }) => {
        this.recipes.set(res.data);
      },
      error: (err) => console.log(err)
    })
  }
}
