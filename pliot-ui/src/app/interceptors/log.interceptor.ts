import { HttpInterceptorFn, HttpRequest, HttpHandler, HttpEvent, HttpHandlerFn } from '@angular/common/http';
import { Observable } from 'rxjs';

export function logInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  console.log("interceptor request ");
  return next(req);
}