import { HttpClient } from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Recipe, RecipeResponse} from '../models/recipes.model';
import {APP} from '../../../envirenement/server';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private http: HttpClient = inject(HttpClient);

  public getAllRecipes(page: number, size: number): Observable<RecipeResponse> {
    return this.http.get<RecipeResponse>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/recipes?page=${page}&size=${size}`);
  }
}
