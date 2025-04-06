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


  getAggregation(level: string , parent?: string): Observable<any[]> {
    let url = `/aggregation?level=${level}`;
    if (parent) url += `&parent=${parent}`;
    return this.webApiService.get(url);
  }

}
