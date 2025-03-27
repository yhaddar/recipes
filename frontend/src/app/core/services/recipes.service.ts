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

  public recipes = signal<(RecipeResponse | null)>(null);
  public loading = signal<boolean>(true);

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
}
