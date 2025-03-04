import { inject, Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHandlerFn } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import Keycloak from 'keycloak-js';
 

export function addBearer(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  alert( "test ");
  const keycloak = inject(Keycloak);
  const token = keycloak.token;
      if (token) {
        const authReq = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` }
        });
        return next(authReq);
      }
      return next(req);
    
}


