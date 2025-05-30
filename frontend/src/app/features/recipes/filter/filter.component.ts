import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Category} from '../../../core/models/Category.model';
import {NgOptimizedImage} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-filter',
  imports: [
    NgOptimizedImage,
    FormsModule
  ],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {
  @Input() categories!: Category[];
  @Input() path_image!: string;
  @Input() totalItems!: number;

  categoryId: string = '';

  @Output() categoryValue: EventEmitter<string> = new EventEmitter();

  setCategoryValue(){
    this.categoryValue.emit(this.categoryId);
  }
}
