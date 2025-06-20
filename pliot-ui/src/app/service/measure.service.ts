import { Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable, of } from 'rxjs';
import { DatePipe } from "@angular/common";

const isMockEnabled = false;

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

     if (isMockEnabled) {
       // ✅ Simulazione di misura
       const mockData = {
         total: 2,
         content: [
           {
             timestamp: new Date().toISOString(),
             signalId: signalId,
             value: Math.random() * 100,
             unit: 'kWh'
           },
           {
             timestamp: new Date(Date.now() - 3600000).toISOString(), // 1 ora fa
             signalId: signalId,
             value: Math.random() * 100,
             unit: 'kWh'
           }
         ]
       };

       return of({ body: mockData });
     } else {

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

}
