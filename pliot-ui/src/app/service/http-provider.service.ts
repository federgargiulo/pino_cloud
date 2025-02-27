import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/internal/operators/catchError';
import { HttpHeaders, HttpClient, provideHttpClient, HttpParams } from '@angular/common/http';
import { LocationStrategy, PathLocationStrategy , Location } from '@angular/common';



@Injectable({ providedIn: 'root' })
export class HttpProviderService {

  BASE_URL:string = "";
  constructor(private httpClient: HttpClient ,
            locationStrateg : LocationStrategy ,
            location: Location ) {

    console.log( "host     " + window.location.host );
    console.log( "protocol     " + window.location.protocol );
    console.log( "location path " + location.path() );
    console.log( "location Strateg " + locationStrateg.path() );
    console.log( "location getBaseHref " + locationStrateg.getBaseHref() );

    var DEV:string = "localhost:4200";
    var LOCAL_HOST:string = "localhost:8080";
    let x : string = "";
    if ( window.location.host.indexOf( DEV ) >= 0 ){
          x = window.location.protocol + "////" + LOCAL_HOST;
     }
     else{
         x = window.location.protocol + "////" + window.location.host;
     }
     this.BASE_URL = x;
  }






  // Get call method
  // Param 1 : url
  get(url: string , paramsObj?: { [key: string]: string } ): Observable<any> {

    let httpParams = new HttpParams();
    httpParams.append("1","1");
    if ( paramsObj ){
      Object.keys(paramsObj).forEach((key) => {
        httpParams = httpParams.append(key, paramsObj[key]);
      });
    }
    const headers = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Cache-Control' : 'no-cache',
        'Pragma' : 'no-cache'
      }),
      observe: "response" as 'body',
      params: httpParams
    };
    return this.httpClient.get( this.BASE_URL + url,   headers   )
    .pipe(
        map((response: any) => this.ReturnResponseData(response)),
        catchError(this.handleError)
    );
  }

  // Post call method
  // Param 1 : url
  // Param 2 : model
  post(url: string, model: any): Observable<any> {
    console.info(" path " + this.BASE_URL + url )
    console.info( "object " +   model.name )
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      observe: "response" as 'body'
    };
    return this.httpClient.post(
      this.BASE_URL + url,
      model,
      httpOptions)
      .pipe(
        map((response: any) => this.ReturnResponseData(response)),
        catchError(this.handleError)

    );
  }

  put(url: string, model: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      observe: "response" as 'body'
    };
    return this.httpClient.put(
      this.BASE_URL + url,
      model,
      httpOptions)
      .pipe(
        map((response: any) => this.ReturnResponseData(response)),
        catchError(this.handleError)

    );
  }

  delete(url: string ): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      observe: "response" as 'body'
    };
    return this.httpClient.delete(
      this.BASE_URL + url,
      httpOptions)
      .pipe(
        map((response: any) => this.ReturnResponseData(response)),
        catchError(this.handleError)

    );
  }

  private ReturnResponseData(response: any) {
    return response;
  }

  private handleError(error: any) {
    return throwError(error);
  }



    patch(url: string, model: any): Observable<any> {
        const httpOptions = {
          headers: new HttpHeaders({
            'Content-Type': 'application/json'
          }),
          observe: "response" as 'body'
        };
        return this.httpClient.patch(
          this.BASE_URL + url,
          model,
          httpOptions)
          .pipe(
            map((response: any) => this.ReturnResponseData(response)),
            catchError(this.handleError)

        );
      }

}

