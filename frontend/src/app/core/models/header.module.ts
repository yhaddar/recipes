import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class HeaderModule {
  id!: number;
  title!: string;
  path!: string;
}
