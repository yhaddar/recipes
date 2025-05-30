import {Component, inject, Input, OnInit, signal} from '@angular/core';
import {Category} from '../../../core/models/Category.model';
import {Carousel} from 'primeng/carousel';
import {NgOptimizedImage} from '@angular/common';
import {CategoriesService} from '../../../core/services/categories.service';

@Component({
  selector: 'app-category',
  imports: [
    Carousel,
    NgOptimizedImage
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {
  @Input() path_image!: string;

  private categoriesService: CategoriesService = inject(CategoriesService);

  protected categories = signal<Category[] | []>([]);

  getAllCategories(): void {
    this.categoriesService.getAllCategories().subscribe((category: { data: Category[] }) => {
      this.categories.set(category?.data);
    });
  }

  ngOnInit(): void {
    this.getAllCategories();
  }

}
