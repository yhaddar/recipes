import {inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, tap} from 'rxjs';
import {Category} from '../models/Category.model';
import {APP} from '../../../envirenement/server';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  private http: HttpClient = inject(HttpClient);
  private categories = signal<Category[] | []>([]);
  protected loading = signal<boolean>(true);

  public getAllCategories(): Observable<{ data: Category[] }> {
    this.loading.set(true);
    return this.http.get<{ data: Category[] }>(`${APP.SERVER_HOST}:${APP.SERVER_PORT}/category/`).pipe(
      tap((res: { data: Category[] }) => {
        this.categories.set(res?.data);
        this.loading.set(false);
      }),
      catchError(error => {
        this.loading.set(true);
        throw error;
      })
    );
  }

}
