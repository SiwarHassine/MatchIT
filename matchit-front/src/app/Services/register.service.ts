import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  constructor(private http: HttpClient, private router: Router) {}

  register(registerForm: FormGroup, fileData: FormData): Observable<any> {
    const user = {
      firstname: registerForm.get('firstname')?.value,
      lastname: registerForm.get('lastname')?.value,
      email: registerForm.get('email')?.value,
      phone: registerForm.get('phone')?.value,
      password: registerForm.get('password')?.value,
      birthdate: registerForm.get('birthdate')?.value,
      documents: {
        name: (fileData.get('file') as File).name,
        type: (fileData.get('file') as File).type,
        data: (fileData.get('file') as File).size,
      },
    };
    return this.http.post<any>('http://localhost:3000/users', user);
  }

  addRec(registerForm: FormGroup): Observable<any> {
    const user = {
      firstname: registerForm.get('firstname')?.value,
      lastname: registerForm.get('lastname')?.value,
      email: registerForm.get('email')?.value,
      role: registerForm.get('role')?.value,
    };
    return this.http.post<any>('http://localhost:3000/users', user);
}


  navigateToLogin(): void {
    this.router.navigate(['login']);
  }
}
