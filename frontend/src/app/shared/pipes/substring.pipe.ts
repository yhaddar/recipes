import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'substring'
})
export class SubstringPipe implements PipeTransform {

  transform(value: string, end: number): unknown {
    return value.length < end ? value : value.slice(0, end).concat("...");
  }

}
