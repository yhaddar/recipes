import {Component, inject, OnInit, Output, signal} from '@angular/core';
import {HeroComponent} from './hero/hero.component';
import {RecipesService} from '../../core/services/recipes.service';
import {RecipeResponse} from '../../core/models/recipes.model';
import {APP} from '../../../envirenement/server';
import {CategoryComponent} from './category/category.component';
import {RecipesComponent} from './recipes/recipes.component';
import {LoadingComponent} from '../../layout/loading/loading.component';
import {take} from 'rxjs';
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
    LoadingComponent,
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
  private readonly size: number = 9;

  protected loading = signal<boolean>(true);
  protected page = signal<number>(0);
  protected recipes = signal<(RecipeResponse | null)>(null);
  protected recipes_without_bg = signal<RecipeResponse | null>(null);

  getFirst9Recipes(): void {
    this.loading.set(true);
    this.recipesService.getAllRecipes(this.page(), this.size).pipe(take(1)).subscribe({
      next: (res: RecipeResponse) => {
        if (res) {
          this.recipes.set(res);
          this.loading.set(false);
        }
      },
      error: (err) => {
        console.log(err);
        this.loading.set(true);
      }
    });
  }

  getSecond8Recipes(): void {
    this.loading.set(true);
    this.recipesService.getAllRecipes(1, 8).pipe(take(1)).subscribe({
      next: (res: RecipeResponse) => {
        if(res){
          this.recipes_without_bg.set(res);
          this.loading.set(false);
        }
      }, error: (err: any) => console.log(err)
    })
  }

  ngOnInit(): void {
    this.getFirst9Recipes();
    this.getSecond8Recipes();

    console.log(this.recipes_without_bg());
  }
}
