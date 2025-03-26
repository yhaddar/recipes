import {Component, inject, Input, OnInit, signal} from '@angular/core';
import {Category} from '../../../core/models/Category.model';
import {Carousel} from 'primeng/carousel';
import {PrimeTemplate} from 'primeng/api';
import {NgOptimizedImage} from '@angular/common';
import {CategoriesService} from '../../../core/services/categories.service';

@Component({
  selector: 'app-category',
  imports: [
    Carousel,
    PrimeTemplate,
    NgOptimizedImage
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {
  @Input() path_image!: string;

  private categoriesService: CategoriesService = inject(CategoriesService);

  protected loading = signal<boolean>(true);
  protected categories = signal<Category[] | []>([]);

  ngOnInit(): void {
    this.loading.set(true);

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
