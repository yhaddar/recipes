import {Component, EventEmitter, inject, OnInit, Output, signal} from '@angular/core';
import {HeroComponent} from './hero/hero.component';
import {RecipesService} from '../../core/services/recipes.service';
import {Recipe} from '../../core/models/recipes.model';
import {APP} from '../../../envirenement/server';
import {CategoryComponent} from './category/category.component';
import {Category} from '../../core/models/Category.model';
import {CategoriesService} from '../../core/services/categories.service';
import {RecipesComponent} from './recipes/recipes.component';
import {LoadingComponent} from '../../layout/loading/loading.component';

@Component({
  selector: 'app-home',
  imports: [
    HeroComponent,
    CategoryComponent,
    RecipesComponent,
    LoadingComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private recipesService: RecipesService = inject(RecipesService);
  private categoriesService: CategoriesService = inject(CategoriesService);

  public recipes = signal<Recipe[] | []>([]);
  public categories = signal<Category[] | []>([]);

  public path_image: string = `${APP.SERVER_HOST}:${APP.SERVER_PORT}`;

  protected loading = signal<boolean>(true);

  ngOnInit(): void {
    this.loading.set(true);
    this.recipesService.getAllRecipes().subscribe({
      next: (res: { data: Recipe[] }) => {
        if (res.data) {
          this.recipes.set(res.data);
          this.loading.set(false);
        }
      },
      error: (err) => {
        console.log(err);
        this.loading.set(true);
      }
    });

    this.categoriesService.getAllCategories().subscribe({
      next: (res: { data: Category[] }) => {
        this.categories.set(res.data);
        this.loading.set(false);
      },
      error: (err) => {
        console.log(err)
        this.loading.set(true);
      }
    });


  }
}
