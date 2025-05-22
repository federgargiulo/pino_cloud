import { Injectable } from '@angular/core';
import { HttpProviderService } from '../service/http-provider.service';
import { Observable } from 'rxjs';


var version="";

var httpLink = {
  findMeasure:  version + "/measure",
  
}

@Injectable({
  providedIn: 'root'
})
export class OlapService {


  constructor( private webApiService: HttpProviderService ) { }


  getAggregation( sigalId: string , dateRange:any , aggregation: string ): Observable<any[]> {
    let url = `/aggregation?signalId=${sigalId}`;
    if ( dateRange!= null ){
     
      if ( dateRange.start  )
         url += "&startDate=" + dateRange.start;
      if ( dateRange.end )  
         url += "&endDate=" + dateRange.end;
    }
    if ( aggregation ) url += `&parent=${aggregation}`;
    return this.webApiService.get(url);
  }

}
