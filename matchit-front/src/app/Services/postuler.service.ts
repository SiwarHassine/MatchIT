import { Injectable } from '@angular/core';
import { HttpClient, HttpParams  } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostulerService {

  constructor(private http: HttpClient) { }

  uploadfile(id: any, file: File){
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(`${environment.apiHost}${environment.uploadCV}/${id}`, formData);
  }

  calculScore(id : any){
    return this.http.get(environment.apiHost+environment.calculScore+`/${id}`);
  }

  reCalculScore(idFile: any, idOffer: any, data: any) {
    const params = new HttpParams({ fromObject: data });
    return this.http.get(environment.apiHost + environment.reCalculScore + `/${idFile}/${idOffer}`, { params });
  }
  

  findApplicationByofferUser(offerId: any, idUser: any){
    return this.http.get(environment.apiHost+environment.getApplicationByidOfferByuserId+`${offerId}/${idUser}`);
  }

  postuler(id : any, data : any){
    return this.http.put(environment.apiHost+environment.postuler+`/${id}`, data);
  }

  getFileByUser(id: any){
    return this.http.get(environment.apiHost+environment.getFileByUserId+`/${id}`);
  }

}
