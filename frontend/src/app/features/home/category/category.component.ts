import {Component, inject, Input, OnInit} from '@angular/core';
import {Category} from '../../../core/models/Category.model';
import { CategoriesService } from '../../../core/services/categories.service';
import {Carousel} from 'primeng/carousel';
import {Button} from 'primeng/button';
import {PrimeTemplate} from 'primeng/api';
import {NgOptimizedImage} from '@angular/common';

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
export class CategoryComponent {
  @Input() categories!: (Category | null)[];
  @Input() path_image!: string;
}
