import {HttpClient, HttpHeaders} from '@angular/common/http';
import {inject, Injectable, signal} from '@angular/core';
import {Direction, Ingredient, Recipe, RecipeResponse} from '../models/recipes.model';
import {APP} from '../../../envirenement/server';
import {catchError, Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private http: HttpClient = inject(HttpClient);

  public loading = signal<boolean>(false);

  public getAllRecipes(page: number, size: number): Observable<RecipeResponse> {
    this.loading.set(true);
    return this.http.get<RecipeResponse>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/recipes?page=${page}&size=${size}`).pipe(
      tap((recipe: RecipeResponse): RecipeResponse => {
        this.loading.set(false);
        return recipe;
      }),
      catchError(error => {
        this.loading.set(true);
        throw error;
      })
    );
  }

  public filterRecipeWithCategory(category_id: string, page: number, size: number) {
    this.loading.set(true);
    return this.http.get<RecipeResponse>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/recipes/filter?category=${category_id}&page=${page}&size=${size}`).pipe(
      tap((recipe: RecipeResponse) => {
        this.loading.set(false);
        return recipe;
      }),
      catchError(err => {
        this.loading.set(false);
        throw err;
      })
    );
  }

  public recipeDetail(id: string) {
    this.loading.set(true);
    return this.http.get<Recipe>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/recipes/detail?id=${id}`).pipe(
      tap((recipe: Recipe) => {
        this.loading.set(false);
        return recipe;
      }),
      catchError((err) => {
        this.loading.set(true);
        throw err;
      })
    )
  }

  public recipeWithCategory(category_id: string, page: number, size: number): Observable<RecipeResponse> {
    this.loading.set(true);
    return this.http.get<RecipeResponse>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/recipes/by-category?category_id=${category_id}&page=${page}&size=${size}`).pipe(
      tap((recipe: RecipeResponse) => {
        this.loading.set(false);
        return recipe;
      }),
      catchError((err) => {
        this.loading.set(true);
        throw err;
      })
    )
  }

  public getIngredient(recipe_id: any): Observable<{ data: Ingredient[] }> {
    this.loading.set(true);
    return this.http.get<{ data: Ingredient[] }>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/ingredient?recipe_id=${recipe_id}`).pipe(
      tap((ingredient: { data: Ingredient[] }): { data: Ingredient[] } => {
        this.loading.set(false);
        return ingredient;
      }),
      catchError(err => {
        this.loading.set(true);
        throw err;
      })
    );
  }

  public getDirection(recipe_id: any): Observable<{ data: Direction[] }> {
    this.loading.set(true);
    return this.http.get<{ data: Direction[] }>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/direction?recipe_id=${recipe_id}`).pipe(
      tap((direction: { data: Direction[] }): { data: Direction[] } => {
        this.loading.set(false);
        return direction;
      }),
      catchError(err => {
        this.loading.set(true);
        throw err;
      })
    );
  }
}
