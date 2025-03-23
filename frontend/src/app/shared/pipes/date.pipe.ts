import { Pipe, PipeTransform } from '@angular/core';
import {format} from 'date-fns';

@Pipe({
  name: 'date'
})
export class DatePipe implements PipeTransform {

  transform(value: string, args: any): any {
    if(!value){
      console.log("error : date not found");
    }else {
      return format(value, args);
    }
  }
}
