import {Component, effect, inject, OnInit, signal} from '@angular/core';
import {HeroComponentComponent} from "../../layout/hero-component/hero-component.component";
import {RecipesService} from '../../core/services/recipes.service';
import {RecipeResponse} from '../../core/models/recipes.model';
import {APP} from '../../../envirenement/server';
import {Recipes2Component} from "../home/recipes2/recipes2.component";
import {CategoriesService} from "../../core/services/categories.service";
import {Category} from "../../core/models/Category.model";
import {InboxComponent} from '../../layout/inbox/inbox.component';
import {FilterComponent} from './filter/filter.component';
import {PaginationComponent} from './pagination/pagination.component';

@Component({
  selector: 'app-recipes',
  imports: [
    HeroComponentComponent,
    Recipes2Component,
    InboxComponent,
    FilterComponent,
    PaginationComponent,
  ],
  templateUrl: './recipes.component.html',
  styleUrl: './recipes.component.css'
})
export class RecipesComponent implements OnInit {
  private recipesService: RecipesService = inject(RecipesService);
  private categoriesService: CategoriesService = inject(CategoriesService);

  protected recipes = signal<RecipeResponse | null>(null)
  protected categories = signal<Category[] | []>([])
  protected path_image: string = `${APP.SERVER_HOST}:${APP.SERVER_PORT}`;
  protected category_id = signal<string>('-');
  protected page = signal<number>(0);

  getAllRecipes(): void {
    this.recipesService.getAllRecipes(this.page(), 16).subscribe((recipe: RecipeResponse) => {
      this.recipes.set(recipe);
    });
  }

  getAllCategories(): void {
    this.categoriesService.getAllCategories().subscribe((category: { data: Category[] }) => {
      this.categories.set(category?.data)
    });
  }

  filterRecipeWithCategory(category_id: string): void {
    this.recipesService.filterRecipeWithCategory(category_id, this.page(), 16).subscribe((recipe: RecipeResponse) => {
      this.recipes.set(recipe);
    })
  }

  constructor() {
    effect(() => {
      this.getAllRecipes();
    });

    effect(() => {

      if(this.category_id() == "-"){
        this.getAllRecipes();
      }else {
        this.filterRecipeWithCategory(this.category_id());
      }
    });
  }

  ngOnInit(): void {
    this.getAllCategories();

    console.log(this.recipes());
  }

  receiveMessage(categoryValue: string) {
    this.category_id.set(categoryValue);
  }

  changePage(page: number) {
    this.page.set(page);
  }
}

