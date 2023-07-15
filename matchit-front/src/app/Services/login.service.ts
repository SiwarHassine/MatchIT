import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  isLogged = false;
  constructor(private _http: HttpClient) { }

  login(email: string, password: string) {
    return this._http.get<any>('http://localhost:3000/users')
      .toPromise()
      .then((res: any) => {
        const user = res.find((a: any) => {
          return a.email === email && a.password === password
        });

        if (user) {
          return { success: true, message: 'you are successfully login' };
        } else {
          return { success: false, message: 'User Not Found' };
        }
      })
      .catch(() => {
        return { success: false, message: 'Something was wrong' };
      });
  }
}
