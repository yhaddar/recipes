import {Component, inject, OnInit, Output, signal} from '@angular/core';
import {HeroComponent} from './hero/hero.component';
import {RecipesService} from '../../core/services/recipes.service';
import {RecipeResponse} from '../../core/models/recipes.model';
import {APP} from '../../../envirenement/server';
import {CategoryComponent} from './category/category.component';
import {RecipesComponent} from './recipes/recipes.component';
import {LoadingComponent} from '../../layout/loading/loading.component';
import {catchError, map, Subscription, take, tap} from 'rxjs';
import {LearnMoreComponent} from '../../layout/learn-more/learn-more.component';
import {InstagramComponent} from './instagram/instagram.component';
import {Recipes2Component} from './recipes2/recipes2.component';
import {InboxComponent} from '../../layout/inbox/inbox.component';

@Component({
  selector: 'app-home',
  imports: [
    HeroComponent,
    CategoryComponent,
    RecipesComponent,
    LearnMoreComponent,
    InstagramComponent,
    Recipes2Component,
    InboxComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private recipesService: RecipesService = inject(RecipesService);

  protected path_image: string = `${APP.SERVER_HOST}:${APP.SERVER_PORT}`;

  protected recipes = signal<RecipeResponse | null>(null)
  protected recipes_without_bg = signal<RecipeResponse | null>(null);

  getFirst9Recipes(): void {
    this.recipesService.getAllRecipes(0, 9).subscribe((recipe: RecipeResponse) => {
      this.recipes.set(recipe);
    });
  }

  getSecond8Recipes(): void {
    this.recipesService.getAllRecipes(1, 8).subscribe((recipe: RecipeResponse) => {
      this.recipes_without_bg.set(recipe);
    });
  }


  ngOnInit(): void {
    this.getFirst9Recipes();
    this.getSecond8Recipes();
  }
}
