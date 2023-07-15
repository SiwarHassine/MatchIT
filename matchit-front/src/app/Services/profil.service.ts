import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  getProfile(id: string): Observable<any> {
    return this.http.get<any>('http://localhost:3000/users/' + id);
  }

  updateProfile(id: string, data: any): Observable<any> {
    return this.http.put<any>('http://localhost:3000/users/' + id, data);
  }

  getProfileById(id: string): Observable<any> {
    return this.http.get<any>('http://localhost:3000/users/' + id);
  }

}
