import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CandidatsService {

  private apiUrl = 'http://localhost:3000/candidats';
  constructor(private http: HttpClient) { }
    // Ajouter une expérience professionnelle pour un utilisateur donné
    addExperience(userId: number, experience: any): Observable<any> {
      const url = `${this.apiUrl}/${userId}/experiences`;
      return this.http.post<any>(url, experience);
    }
   
    updateUserWithExperience(user: any, experience: any): Observable<any> {
   
      const url = `${this.apiUrl}/${user.id}`;
      user.experiences.push(experience);
      return this.http.put<any>(url, user);
    }

    updateUser(id: string, data: any): Observable<any> {
      return this.http.put<any>(`${this.apiUrl}/${id}`, data);
    }
    
    getUserById(id: number): Observable<any> {
      const url = `${this.apiUrl}/${id}`;
      return this.http.get<any>(url);
    }
    
    getUsers(): Observable<any> {
      const url = `${this.apiUrl}`;
      return this.http.get<any>(url);
    }


    getFileCV(id : any){
      return this.http.get(environment.apiHost+environment.getCVCandidat+`/${id}`);
    }

    getFileIdByUser(id :any){
      return this.http.get(environment.apiHost+environment.getFileByUserId+`/${id}`);
    }

    prediction( data: any){
      return this.http.post(environment.apiHost+environment.prediction, data);
    }
  
}
