import { Injectable } from '@angular/core';
import { HttpProviderService } from './http-provider.service';
import { Observable } from 'rxjs';



var version="";

var httpLink = {
  getAllGroups:  version + "/configuration/allGroups",
}


@Injectable({
  providedIn: 'root'
})
export class CommonService {
   constructor(private webApiService: HttpProviderService   ) { }
  
    public getAllGreoups(): Observable<any> {
      return this.webApiService.get(httpLink.getAllGroups );
    }
 
 
}
