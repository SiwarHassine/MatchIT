import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { user, userauth } from '../models/models';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private readonly usersUrl = 'http://localhost:3000/users';
  user!:user; 
    constructor(private http:HttpClient) { }
    logOut()
   {
     sessionStorage.removeItem("token");
     sessionStorage.removeItem("user");
   }
   getRecruteurs(): Observable<any[]> {
    return this.http.get<any[]>(environment.apiHost+environment.getuserbyrole+"RECRUTEUR");
  }
  
  getCandidats(): Observable<any[]> {
    return this.http.get<any[]>(environment.apiHost+environment.getuserbyrole+"CANDIDAT");
  }
  updateRecruteur(user: any): Observable<any>{
    // Fusionner les attributs mis à jour avec l'objet utilisateur existant
    const updatedUser = {
      ...this.user,
      ...user,
    };
    return this.http.put<any>(environment.apiHost + environment.updateuser, updatedUser)
    .pipe(map(userdata => {
      this.user = userdata.user;
      return this.user;
    }));
  }
  deleteRecruteur(recId: any): Observable<any>{
    return this.http.delete(`${this.usersUrl}/${recId}`);
  }
  getRecruteurById(id: any): Observable<any> {
    return this.http.get<any>(`${this.usersUrl}/${id}`);
  }
  

    getAll():Observable<[]>{
     return this.http.get<[]>(environment.apiHost+environment.getallusers)
    }
    getUser(userid:any):Observable<[]>{
      return this.http.get<[]>(environment.apiHost+environment.getuser+userid)
     }
 auth(user: any ):Observable<any>{
      return this.http.post<any>(environment.apiHost+environment.authuser,user)
      .pipe(map(userdata =>{
        console.log(userdata.tokken)
        sessionStorage.setItem("token",JSON.stringify(userdata.tokken));
        sessionStorage.setItem("user",JSON.stringify(userdata.user));
        this.user=userdata.user;
       return this.user 
      }))
     }
     delete(userid: any):Observable<[]>{
      return this.http.delete<[]>(environment.apiHost+environment.deleteuser+userid)
     }

  update(user: any): Observable<any> {
      // Fusionner les attributs mis à jour avec l'objet utilisateur existant
      const updatedUser = {
        ...this.user,
        ...user,
      };
      return this.http.put<any>(environment.apiHost + environment.updateuser, updatedUser)
      .pipe(map(userdata => {
        this.user = userdata.user;
        return this.user;
      }));
    }

   updateCV(id : any, file : any){
    const formData = new FormData();
    formData.append('file', file);
    return this.http.put(environment.apiHost+environment.updateCV+id, formData);
    }
    
  
     reg(user : any):Observable<[]>{
      return this.http.post<[]>(environment.apiHost+environment.reguser,user)
     }
     newpwd(user:any):Observable<[]>{
      return this.http.put<[]>(environment.apiHost+environment.newpwdlink,user)
     }
  }