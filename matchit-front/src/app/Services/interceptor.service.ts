import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor {
  token: any = ''
  constructor() { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("testint interceptor", req)
    const tok = sessionStorage.getItem("token")
    console.log("interceptorlocalstorge "+tok)

    if (tok !== null) {
      var obj = JSON.parse(tok);
      this.token = obj.jwt
      console.log("in condition "+obj.jwt);
    }
    console.log("my value token "+this.token)
    req = req.clone({
      headers: req.headers.set('Authorization',  "Bearer "+this.token),
    })
    /* req=req.clone({
     setHeaders: {
      'Access-Control-Allow-Origin':'*'
    }
    })*/


    /*if(req.method==="POST"){
      
      req=req.clone({
        setHeaders: {
          'Accept': 'application/json, text/plain',
         'Access-Control-Allow-Origin':'*',
         'Content-Type': 'application/json',
       }
    })}*/
    console.log("testint interceptor", req)
    return next.handle(req)
  }
}
