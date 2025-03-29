import { HttpClient, HttpHeaders } from '@angular/common/http';
import {inject, Injectable, signal} from '@angular/core';
import {Recipe, RecipeResponse} from '../models/recipes.model';
import {APP} from '../../../envirenement/server';
import {catchError, Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private http: HttpClient = inject(HttpClient);
  private httpHeaders = new HttpHeaders({
    "Cache-Control": "no-cache",
  });

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

  public filterRecipeWithCategory(category_id: string, page: number, size: number){
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
}
