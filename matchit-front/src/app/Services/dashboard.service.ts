import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  apiUrl = 'http://localhost:8087/similarity/';
  
    constructor(private http: HttpClient) { }
  
    getScore() {
      return this.http.get(this.apiUrl + 'score');
    }
}
