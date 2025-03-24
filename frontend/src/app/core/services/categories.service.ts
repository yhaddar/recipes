import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../models/Category.model';
import {APP} from '../../../envirenement/server';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  private http: HttpClient = inject(HttpClient);

  public getAllCategories(): Observable<{ data: Category[] }> {
    return this.http.get<{ data: Category[] }>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/category/`);
  }

}
