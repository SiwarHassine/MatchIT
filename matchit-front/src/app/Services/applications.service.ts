import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment'


@Injectable({
  providedIn: 'root'
})
export class ApplicationsService {

  constructor(private http: HttpClient) { }

  getApplications(){
    return this.http.get(environment.apiHost+environment.getallapplication);
  }

  getApplicationsOrderByMonth(){
    return this.http.get(environment.apiHost+environment.getApplicationOrderByMonth);
  }

  getAllApplicationPaginated(pageNumber: number, pageSize: number) {
    return this.http.get(environment.apiHost+environment.getapplicationPaginated + `${pageNumber}/${pageSize}`);
  }

  getApplicationByUserId(userId : any): Observable<[any]>{
    return this.http.get<[any]>(environment.apiHost+environment.getApplicationByuserId+userId);
  }

  getApplicationByUserIdPaginated(userId : any, pageNumber: number, pageSize: number): Observable<[any]>{
    return this.http.get<[any]>(environment.apiHost+environment.getApplicationByUserIdPaginated+userId+ `/${pageNumber}/${pageSize}`);
  }

  getApplicationByIdOffer(idoffer : any): Observable<[any]>{
    return this.http.get<[any]>(environment.apiHost+environment.getApplicationByidoffer+idoffer);
  }

  deleteApplication(id : any){
    return this.http.delete(environment.apiHost+environment.deleteApplication+ `/${id}`);
  }
}
