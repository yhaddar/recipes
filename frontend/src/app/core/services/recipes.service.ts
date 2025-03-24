import { HttpClient } from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Recipe} from '../models/recipes.model';
import {APP} from '../../../envirenement/server';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private http: HttpClient = inject(HttpClient);

  public getAllRecipes(): Observable<{ data: Recipe[] }> {
    return this.http.get<{ data: Recipe[] }>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/recipes/`);
  }
}
