import { Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable } from 'rxjs';
import { DatePipe } from "@angular/common";



var version="";

var httpLink = {
  findMeasure:  version + "/measure",
  
}
var PAGE = 0;

var PAGE_SIZE = 100;


@Injectable({
  providedIn: 'root'
})
export class MeasureService {


  constructor(private webApiService: HttpProviderService , private datePipe: DatePipe   ) { }
  
    getYesterdaysDate() {
      var date = new Date();
      date.setDate(date.getDate()-1);
      return date.getDate() + '/' + (date.getMonth()+1) + '/' + date.getFullYear();
    }

   public findMeasures( signalId: string, ): Observable<any> {
      let dte = new Date();
      dte.setDate(dte.getDate() - 2);
      
      let dateStr = this.datePipe.transform( dte , 'YYYY/MM/dd')  
      

      return this.webApiService.get( httpLink.findMeasure ,
             { signalId: signalId , 
               from: ''+dateStr,
               page: '0' ,
               pageSize : '100' } );
    }
    
}
