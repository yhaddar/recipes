import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-pagination',
  imports: [],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() page!: number;
  @Input() lastPage!: number;

  @Output() pageChange = new EventEmitter<number>();

  increment(){
    this.pageChange.emit(this.page += 1);
  }

  decrement(){
    this.pageChange.emit(this.page -= 1);
  }
}
