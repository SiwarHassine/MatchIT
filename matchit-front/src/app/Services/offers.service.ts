import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  apiUrl = 'http://localhost:8089/';

  constructor(private http: HttpClient) { }

  getOffers() {
    return this.http.get(environment.apiHost+environment.listOffer);
  }

  getOfferById(id: any) {
    return this.http.get<any>(environment.apiHost+environment.getOfferbyId+`/${id}`);
  }
  addOffer(data: any) {
    return this.http.post(environment.apiHost+environment.addOffer, data);
  }

  updateOffer(id: any, data: any) {
    return this.http.put(environment.apiHost+environment.updateOffer+`/${id}`, data);
  }

  deletOffer(id: any) {
    return this.http.delete(environment.apiHost+environment.deleteOffer+`/${id}`);
  }

  getAllOffersPaginated(pageNumber: number, pageSize: number) {
    return this.http.get(environment.apiHost+environment.pagination + `${pageNumber}/${pageSize}`);
  }


  getAllOffersNotExPaginated(pageNumber: number, pageSize: number) {
    return this.http.get(environment.apiHost+environment.getOfferNotExpired + `/${pageNumber}/${pageSize}`);
  }
  
  getAllOffersByStatus(pageNumber: number, pageSize: number,  field: String) {
    return this.http.get(environment.apiHost+environment.getOfferByStatus+ `/${pageNumber}/${pageSize}/${field}`);
  }

  getAllOffersNotExpired() {
    return this.http.get(environment.apiHost+environment.getOfferNotExp);
  }
  calculScoreAllFiles(idOffer : any){
    return this.http.get(environment.apiHost+environment.scoreAllFiles+idOffer);
  }
}
